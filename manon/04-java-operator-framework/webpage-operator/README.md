## Steps to load webpage operator sample

1. Install Openshift Pipelines (version <= 1.9)

1.  Clone [java-operator-sdk](https://github.com/java-operator-sdk/java-operator-sdk/tree/main)

`git clone https://github.com/java-operator-sdk/java-operator-sdk/tree/main`

2. Create a project `webpage-operator`

`oc new-project webpage-operator`

3. Do the following changes in the code inside `sample-operators/webpage`:

    1. As we are using non-privileged containers, we need to use an [unprivileged nginx image](https://hub.docker.com/layers/nginxinc/nginx-unprivileged/1.17.9/images/sha256-913ce106a784ca6488e71a4c1aa9f75aca8dde1a0664d754e08358a7041a0217?context=explore) to replace the [nginx:1.17.0](https://github.com/java-operator-sdk/java-operator-sdk/blob/b6b457564059830a8d3feb73af74f21fb765f2ad/sample-operators/webpage/src/main/resources/io/javaoperatorsdk/operator/sample/deployment.yaml#L17-L17) image

    2. As stated in this new image [documentation](https://hub.docker.com/r/nginxinc/nginx-unprivileged) the default port is now 8080 so we need to update at least the [`targetPort`](https://github.com/java-operator-sdk/java-operator-sdk/blob/b6b457564059830a8d3feb73af74f21fb765f2ad/sample-operators/webpage/src/main/resources/io/javaoperatorsdk/operator/sample/service.yaml#L10-L11)

    3. Add the namespace (is mandatory) to the [`webpage-operator`](https://github.com/java-operator-sdk/java-operator-sdk/blob/b6b457564059830a8d3feb73af74f21fb765f2ad/sample-operators/webpage/k8s/operator.yaml#L60-L60) service account. 
		 ```
		 subjects:
		- kind: ServiceAccount
		   name: webpage-operator
		+  namespace: webpage-operator
		 roleRef:
		```
    4. Delete [this line](https://github.com/java-operator-sdk/java-operator-sdk/blob/b6b457564059830a8d3feb73af74f21fb765f2ad/sample-operators/webpage/src/main/resources/io/javaoperatorsdk/operator/sample/service.yaml#L12-L12) to use a `ClusterIP` service instead of a `NodePort`

4. Compile the project with `mvn install`
5. Build the container image with `jib:dockerbuild` mvn plugin
6. Retag the image to the openshift registry

`podman tag localhost/webpage-operator default-route-openshift-image-registry.apps-crc.testing/webpage-operator/webpage-operator`

7. Login to the openshift registry

`podman login -u kubeadmin -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing --tls-verify=false`

8. Push the image

`podman push default-route-openshift-image-registry.apps-crc.testing/webpage-operator/webpage-operator`

9. Check the digest of the `BuildImage` in Openshift and update the [image information](https://github.com/java-operator-sdk/java-operator-sdk/blob/b6b457564059830a8d3feb73af74f21fb765f2ad/sample-operators/webpage/k8s/operator.yaml#L32-L32) of the operator Deployment 

		```
		       containers:
		       - name: operator
		-        image: webpage-operator
		-        imagePullPolicy: Never
		+        image: image-registry.openshift-image-registry.svc:5000/webpage-operator/webpage-operator:latest@sha256:d22011a1bd46d753fba0d803017b2285e2d1c86c8250742b0f6c147f0c0819d7
		+        imagePullPolicy: IfNotPresent
		         ports:
		
		```

10. Create the CRD
`oc apply -f target/classes/META-INF/fabric8/webpages.sample.javaoperatorsdk-v1.yml`
11. Create the operator
`oc apply -f k8s/operator.yaml`
12. Create the webapi custom resource
`oc apply -f k8s/webapi.yaml`
13. Create a route
`oc create route edge hellows --service=hellows`