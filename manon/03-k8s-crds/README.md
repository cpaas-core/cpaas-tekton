# Summary of investigation about Kubernetes Framework

## Operator

Composed by:
- [Custom Resource](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/)
- [Custom Controller](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/#custom-controllers)

## Custom Resource

Can be added _programmingless_ or with a custom served API adhered to de standard API:
- [CRD](https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/)
- [API Aggregation](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/apiserver-aggregation/)

## Controller

The logic of managing the custom resources.

### Framework Types

- _Static_ templated operators, e.g:
	- HELM 
	- KUDO
- Dynamic (more code than the previous one), e.g:
	- Java Operator Framework
	- Operator Framework (compatible with HELM and kubebuilder)
	- kubebuilder 

Here is the [list of Frameworks](https://kubernetes.io/docs/concepts/extend-kubernetes/operator/#writing-operator) available

- [Java Operator Framework](https://github.com/java-operator-sdk/java-operator-sdk) is the one used in [[Agogos]] 
- Go [Operator SDK](https://sdk.operatorframework.io/docs/building-operators/golang/quickstart/) with [kubebuilder](https://book.kubebuilder.io/introduction.html) [under the hood](https://sdk.operatorframework.io/docs/faqs/#what-are-the-the-differences-between-kubebuilder-and-operator-sdk) is used in [[HACBS]]

## Other References

Medium:
- [about Operators](https://medium.com/swlh/kubernetes-operator-for-beginners-what-why-how-21b23f0cb9b1)
- [about Java Operator Framework](https://levelup.gitconnected.com/first-try-on-java-operator-sdk-5a07f30771de)
- [advanced Operators](https://medium.com/swlh/advanced-kubernetes-operators-development-988edad5f58a)

RH Developer:
- [about Java Operator Framework](https://developers.redhat.com/articles/2022/02/15/write-kubernetes-java-java-operator-sdk#)

RH Openshift:
- [Java based operators](https://docs.openshift.com/container-platform/4.11//operators/operator_sdk/java/osdk-java-tutorial.html)

## Useful exercises

-  Tutorial of kubebuilder
	- [ ] [UserIdentity](https://medium.com/swlh/kubernetes-operator-for-beginners-what-why-how-21b23f0cb9b1#d8b4) in [Github](https://github.com/slaise/operator-test)
	- [ ] [CronJob](https://kubebuilder.io/cronjob-tutorial/cronjob-tutorial.html) in [Github](https://github.com/kubernetes-sigs/kubebuilder/tree/master/docs/book/src/cronjob-tutorial/testdata/project)
- Tutorial of Java Operator Framework
	- [ ] [UserIdentity](https://levelup.gitconnected.com/first-try-on-java-operator-sdk-5a07f30771de#539f) in [Github]()
- Tutorial of Operator SDK
	- [ ] [MemCached](https://sdk.operatorframework.io/docs/building-operators/golang/tutorial/) with go in [Github](https://github.com/operator-framework/operator-sdk/tree/master/testdata)
 - KUDO
	- [ ] [4 kudo tutorials](https://github.com/realmbgl/kudo-tutorial#develop-kudo-operators)
 
## Sample Operator implementations

- [Java Operator Framework](https://github.com/java-operator-sdk/java-operator-sdk/tree/main/sample-operators)
- [Operator SDK](https://github.com/operator-framework/operator-sdk/tree/master/testdata) with Go, Ansible and HELM