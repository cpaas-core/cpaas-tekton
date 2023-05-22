1. Install Openshift pipelines operator (version <= 1.9)
2. Clone repository
3. Compile
`mvn install`
4. Create a new project
`oc new-project oc new-project cron-pipeline-job-operator`
5. Create the CRD
`oc apply -f target/classes/META-INF/fabric8/cronpipelinejobs.triggers.tekton.manon-v1.yml`
6. Create the container image with mvn plugin `jib:dockerbuild`
7. Login into CRC container registry of the project
`podman login -u kubeadmin -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing --tls-verify=false`
8. Tag the created image
`podman tag localhost/josdk-cpj-operator default-route-openshift-image-registry.apps-crc.testing/cron-pipeline-job-operator/josdk-cpj-operator`
9. Push the image
`podman push default-route-openshift-image-registry.apps-crc.testing/cron-pipeline-job-operator/josdk-cpj-operator --tls-verify=false`
10. Create the operator
`oc apply -f k8s/operator.yaml`
11. Create the cronpipelinebuild and dependant resources
`oc apply -f k8s/crj-sample.yaml`
