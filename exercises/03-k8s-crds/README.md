# Kubernetes CustomResourceDefinitions

In this exercise you are expected to read these three documents:

* https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/
* https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/
* https://kubernetes.io/docs/concepts/extend-kubernetes/operator/

They will introduce you to the concept of CustomResourceDefinitions (CRDs for short),
guide you to implement a custom CRD called `crontabs.stable.example.com` and introduce
you to the concept of operators.

You are expected to come up with your idea for an operator and deliver one or more CRDs that
will be used by said operator. You will be writing that operator in exercise 04. For example:

> I want to build an operator that given a CRD will build an image, push it to a
registry and then create a Deployment and a Service to access the application.

The main point of this exercise is getting you started in the CRDs/Operator
world and make you think about how to implement an operator.
