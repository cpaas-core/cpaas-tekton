# Summary of investigation about Kubernetes Framework

## Operator

Composed by:
- [Custom Resource](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/)
- [Custom Controller](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/#custom-controllers)

## Custom Resource

Can be added _programmingless_ or with a custom served API adhered to de standard API:
- [CRD](https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/)
- [API Aggregation](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/apiserver-aggregation/)

## Custom Controller

The logic of managing the custom resources.

### Framework Types

- _Static_ templated operators, e.g:
	- [Operator Framwork with HELM](https://sdk.operatorframework.io/docs/building-operators/helm/quickstart/)
	- [KUDO](https://kudo.dev/docs/)
- Dynamic (more code than the previous one), e.g:
	- [Java Operator Framework](https://github.com/java-operator-sdk/java-operator-sdk)
	- [Operator Framework with go](https://sdk.operatorframework.io/docs/building-operators/golang/)
	- [kubebuilder](https://book.kubebuilder.io/)
	- [KOPF](https://kopf.readthedocs.io/en/stable/)

Here is a [list of Frameworks](https://kubernetes.io/docs/concepts/extend-kubernetes/operator/#writing-operator) available

- [Quarkus Operator Framework](https://quarkus.io/extensions/io.quarkiverse.operatorsdk/quarkus-operator-sdk) based on Java Operator Framework is the one used in [[Agogos]] 
- Go [Operator SDK](https://sdk.operatorframework.io/docs/building-operators/golang/quickstart/) with [kubebuilder](https://book.kubebuilder.io/introduction.html) [under the hood](https://sdk.operatorframework.io/docs/faqs/#what-are-the-the-differences-between-kubebuilder-and-operator-sdk) is used in [[HACBS]]

## Other References

Medium:
- [about Operators](https://medium.com/swlh/kubernetes-operator-for-beginners-what-why-how-21b23f0cb9b1)
- [about Java Operator Framework](https://levelup.gitconnected.com/first-try-on-java-operator-sdk-5a07f30771de)
- [advanced Operators](https://medium.com/swlh/advanced-kubernetes-operators-development-988edad5f58a)

RH:
- [about Java Operator Framework](https://developers.redhat.com/articles/2022/02/15/write-kubernetes-java-java-operator-sdk#)
- [about choosing the Operator Framework](https://cloud.redhat.com/blog/build-your-kubernetes-operator-with-the-right-tool)

## Useful exercises

-  Tutorial of kubebuilder
	- [ ] [UserIdentity](https://medium.com/swlh/kubernetes-operator-for-beginners-what-why-how-21b23f0cb9b1#d8b4) in [Github](https://github.com/slaise/operator-test)
	- [ ] [CronJob](https://kubebuilder.io/cronjob-tutorial/cronjob-tutorial.html) in [Github](https://github.com/kubernetes-sigs/kubebuilder/tree/master/docs/book/src/cronjob-tutorial/testdata/project)
- Tutorial of Java Operator Framework
	- [ ] [UserIdentity](https://levelup.gitconnected.com/first-try-on-java-operator-sdk-5a07f30771de#539f) in [Github]()
	- [ ] [Memcached](https://docs.openshift.com/container-platform/4.11//operators/operator_sdk/java/osdk-java-tutorial.html) for Openshift
- Tutorial of Operator SDK
	- [ ] [MemCached](https://sdk.operatorframework.io/docs/building-operators/golang/tutorial/) with go in [Github](https://github.com/operator-framework/operator-sdk/tree/master/testdata) - Also in [Openshift docs](https://docs.openshift.com/container-platform/4.9/operators/operator_sdk/golang/osdk-golang-tutorial.html)
 - Tutorial of KUDO
	- [ ] [4 kudo tutorials](https://github.com/realmbgl/kudo-tutorial#develop-kudo-operators)
 - Tutorial of KOPS
	- [ ] [EphemeralVolumeClaim](https://kopf.readthedocs.io/en/stable/walkthrough/problem/) in [Github](https://github.com/nolar/ephemeral-volume-claims)
 
## Sample Operator implementations

- [Java Operator Framework](https://github.com/java-operator-sdk/java-operator-sdk/tree/main/sample-operators)
- [Operator SDK](https://github.com/operator-framework/operator-sdk/tree/master/testdata) with Go, Ansible and HELM
- [Quarkus Operator SDK](https://github.com/quarkiverse/quarkus-operator-sdk/tree/main/samples)