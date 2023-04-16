Deployment instructions: 


1. Start CRC cluster:

crc cleanup

crc setup

crc start -p pull-secret.txt 

eval $(crc oc-env)

2. Give cluster-admin permissions to developer:

    oc login -u kubeadmin -p <password> https://api.crc.testing:6443

    oc adm policy add-cluster-role-to-user cluster-admin developer

    oc logout

    oc login -u developer -p developer https://api.crc.testing:6443

3. Create new project:

    oc new-project operator-test

4. Build and deploy operator package:

    ./mvnw clean package -Dquarkus.container-image.build=true -DskipTests

5. Create new app on CRC cluster with new image:

    oc get is

    oc new-app --name=operator-app operator-test/operator-test:1.0.0-SNAPSHOT

6. Expose app (not sure if it is nessessary):

    oc get svc

    oc expose svc/operator-app

7. Give permissions to service account:

    kubectl get sa

    oc policy add-role-to-user view system:serviceaccount:operator-test:default

    oc policy add-role-to-user edit system:serviceaccount:operator-test:default

9. Test the operator:

  Deploy nginx pod:

    kubectl apply -f pod.yaml 

  Check the logs of the operator pod:

    oc get pods

    oc logs <podname>


You should see something like this: 

```
Found resource:  name=operator-app-5777f9db4d-xvs4t version=45033
Found resource:  name=operator-test-1-build version=41212
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2023-04-16 09:20:07,634 INFO  [io.qua.ope.run.OperatorProducer] (main) Quarkus Java Operator SDK extension 5.1.1 (commit: 14a149c on branch: 14a149cea9fd57f14c9a6251411dca00d3807011) built on Thu Mar 02 20:32:32 GMT 2023
2023-04-16 09:20:07,652 WARN  [io.qua.ope.run.AppEventListener] (main) No Reconciler implementation was found so the Operator was not started.
2023-04-16 09:20:07,860 INFO  [io.quarkus] (main) operator-test 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.6.Final) started in 2.652s. Listening on: http://0.0.0.0:8080
2023-04-16 09:20:07,860 INFO  [io.quarkus] (main) Profile prod activated. 
2023-04-16 09:20:07,861 INFO  [io.quarkus] (main) Installed features: [cdi, kubernetes, kubernetes-client, openshift-client, operator-sdk, smallrye-context-propagation, smallrye-health, vertx]
Received: ADDEDevent for  name=operator-test-1-build version=41212
Received: ADDEDevent for  name=operator-app-5777f9db4d-xvs4t version=45033
Received: ADDEDevent for  name=nginx version=46132
Received: MODIFIEDevent for  name=nginx version=46133
Received: MODIFIEDevent for  name=nginx version=46135
Received: MODIFIEDevent for  name=nginx version=46140
Received: MODIFIEDevent for  name=nginx version=46200
```

Materials:

https://quarkus.io/version/main/guides/deploying-to-openshift

https://www.youtube.com/watch?v=VSDV4qH9mQc

https://www.youtube.com/watch?v=Q9nuMJ6usFY&t=810s


