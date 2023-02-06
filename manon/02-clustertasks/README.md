# Solution for exercise 02-clustertasks

## Requirements

- Openshift cluster with Tekton/Openshift Pipelines Operator installed

*NOTE*: Deployed in CRC and OCP-C1

## Instructions

1. Login to the cluster
2. Update `<namespace>` of `pipeline.yaml` with the name of the project that you ar using
3. Create tekton resources from files

```
oc create -f manon/02-clustertasks/
```

4. Access the UI and run the created pipeline with a `volumeClaimTemplate` for the workspace (expand the advance options to use 1 MB instead of the default 1 GB)

## Clustertask used

- `git-clone`
- `s2i-java`
- `openshift-client`

## Notes

- Unable to push the image to an external repo (e.g. quay.io). See [this issue](https://github.com/tektoncd/operator/issues/1105)
- Not used resolvers
