# Instructions:

1. Manually install Red Hat OpenShift Pipelines. From the UI: Operators tab -> OperatorHub -> Search "Red Hat OpenShift Pipelines" -> Install.
2. Install Tekton cli
```bash
sudo dnf copr enable chmouel/tektoncd-cli
sudo dnf install tektoncd-cli
```
3. Create a project in OpenShift.
```bash
 oc new-project clustertasks-test
```
4. Install git-clone ClusterTask
```bash
tkn hub install task git-clone --version 0.7
```
5. Install kaniko Task to build and upload a container image.
```bash
tkn hub install task kaniko
```
6. Create a secret with the repository credentials. (dockerhub, quay, etc)
Replace the xxxxx with the content of `~/.docker/config.json` encoded in base64
```bash
cat ~/.docker/config.json | base64 -w0
```
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: docker-credentials
data:
  config.json: xxxxxx
```
```bash
oc apply -f docker-credencials.yml
```
7. Create the pipeline in the project
```bash
oc apply -f clone-build.yml
```
8. Run the pipeline using the pipelineRun object.
```bash
oc create -f clone-build-run.yml
```
