# Solution for exercise 02-clustertasks

## Requirements

- Openshift cluster with Tekton/Openshift Pipelines Operator installed

*NOTE*: Deployed in CRC and OCP-C1

## Instructions

1. Login to the cluster
2. Update `<namespace>` of `pipeline.yaml` with the name of the project that you ar using
3. Create tekton resources from files

`oc apply -f manon/02-clustertasks/pipeline/`

4. Access UI to see the 3 JAVA apps deployed

## Using other container registries

Example with `quay.io` and `podman`

- Login to quay.io with podman

`podman login quay.io`

- Create a secret with the auth file

`oc create secret generic regcred --from-file=.dockerconfigjson=${XDG_RUNTIME_DIR}/containers/auth.json --type=kubernetes.io/dockerconfigjson`

- Link the secret with the pipeline sa

`oc secrets link pipeline regcred`

## Clustertask used

- `git-clone`
- `s2i-java`
- `openshift-client`

## Notes

- Not used resolvers

