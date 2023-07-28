"""
Operator controller
kopf run cronprint_controller.py --verbose
"""
import yaml
import kopf
import kubernetes


def get_cron_job_body(namespace, name, schedule, text):
    return yaml.safe_load(f"""
        apiVersion: batch/v1
        kind: CronJob
        metadata: 
            name: {name}
            namespace: {namespace}
        spec:
            schedule: "{schedule}"
            jobTemplate:
                spec:
                    template:
                        spec:
                            containers:
                            - name: "echo"
                              image: alpine
                              env:
                              - name: TEXT
                                value: {text}
                              command: 
                                 - /bin/sh
                                 - -c
                                 - "echo $TEXT"
                            restartPolicy: Never
    """)


@kopf.on.create('cronprints')
def create_fn(spec, name, namespace, logger, **kwargs):
    schedule = spec["cronSpec"]
    text = spec["text"]

    deploy_spec = get_cron_job_body(namespace, name, schedule, text)

    kopf.adopt(deploy_spec)
    try:
        kubernetes.config.load_kube_config()  # for local testing
    except kubernetes.config.ConfigException:
        kubernetes.config.load_incluster_config()  # for clusters
    api = kubernetes.client.BatchV1Api()
    res = api.create_namespaced_cron_job(namespace=namespace, body=deploy_spec)
    logger.debug(res)
    logger.info(f"{name} CronPrint created")


@kopf.on.update('cronprints')
def update(spec, name, namespace, logger, **kwargs):
    schedule = spec["cronSpec"]
    text = spec["text"]

    api = kubernetes.client.BatchV1Api()
    res = api.patch_namespaced_cron_job(name=name, namespace=namespace,
                                        body=get_cron_job_body(namespace, name, schedule, text))
    logger.debug(res)
    logger.info(f"{name} CronPrint updated")
