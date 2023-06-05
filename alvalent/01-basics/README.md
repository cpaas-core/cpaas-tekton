# 01 Basics

[Tekton documentation](https://tekton.dev/docs/)

Related topics:

* Tasks
* Pipelines
* Workspaces

In this exercise you have to create at least two tasks that are called in a single pipeline and that share a workspace. The first one will write the string provided in a parameter to the shared workspace and the second one will output its value.

This assignement also includes some reading. Be sure to read the [Task recommendations](https://github.com/tektoncd/catalog/blob/main/recommendations.md) provided by Tekton, and the [Emitting results](https://github.com/tektoncd/pipeline/blob/main/docs/tasks.md#emitting-results) section **completely**. The second one explains why we are using a workspace instead of the mechanism used there.

**Notes**

* Feel free to use Code Ready Containers to do this, however the OCP-C1 cluster already has the Tekton Operator installed.
* Use images from a registry that is not DockerHub, as you will likely hit the pull rate DockerHub imposes to unathenticate users. You can [search for containers here](https://catalog.redhat.com/software/containers/search).
