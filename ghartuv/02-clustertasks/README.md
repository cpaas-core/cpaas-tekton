# 02 ClusterTasks

In this exercise you have to create a pipeline that uses a ClusterTask to build something, the point is to use more complex tasks. Feel free to use your own tasks too. ClusterTasks are tasks that are preinstalled cluster-wide for all Tekton users to use. Inspect the cluster tasks you have available in your installation, read briefly some of their documentation and create a pipeline that uses it. The documentation can be read using `oc describe clustertasks.tekton.dev <task-name>` or `tkn clustertasks describe <task-name>`.

**Bonus points**: `ClusterTasks` are deprecated and the replacement is called [cluster resolver](https://tekton.dev/docs/pipelines/cluster-resolver/). Use it to reference the ClusterTask you want instead of `taskRef`.
