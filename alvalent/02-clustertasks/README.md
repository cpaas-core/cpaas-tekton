# 02 ClusterTasks

In this exercise you have to create a pipeline that uses a ClusterTask to build something, the point is to use more complex tasks. Feel free to use your own tasks too. ClusterTasks are tasks that are preinstalled cluster-wide for all Tekton users to use. Inspect the cluster tasks you have available in your installation, read briefly some of their documentation and create a pipeline that uses it. The documentation can be read using `oc describe clustertasks.tekton.dev <task-name>` or `tkn clustertasks describe <task-name>`.

**Bonus points**: `ClusterTasks` are deprecated and the replacement is called [cluster resolver](https://tekton.dev/docs/pipelines/cluster-resolver/). Use it to reference the ClusterTask you want instead of `taskRef`.

**Solution**: 

- **Cluster resolver**:
  - Follow [this guide](https://cloud.redhat.com/blog/migration-from-clustertasks-to-tekton-resolvers-in-openshift-pipelines) if you want to migrate ClusterTasks to resolvers.
  - I've created another namespace called `test-pipelines` and imported the task `write-string`
  - Imported `02-clustertasks-pipeline` in `tekton-pipelines` namespace
  - Created a pipeline run in `tekton-pipelines` using `02-clustertasks-pipeline`
- **Git resolver**:
  - Took [this](https://github.com/tektoncd/catalog/tree/main/task/git-clone/0.9) pipeline definition from Tekton catalog
  - Implemented the pipeline and relative pipelinerun