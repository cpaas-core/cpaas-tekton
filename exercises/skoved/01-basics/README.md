# Solution for Exercise 01-basics
## Requirements
- Cluster with Tekton/Openshift Pipelines installed
- tkn (Tekton CLI) installed

## Instructions
1. login to cluster and go to your project
2. create tekton resources from files 

        oc create -f write-file-task.yaml 
        oc create -f read-file-task.yaml 
        oc create -f pipeline.yaml

3. to run the pipeline

        tkn pipeline start read-write-pipeline \
        -w name=shared-workspace,volumeClaimTemplateFile=pvc.yaml \
        -p message="This is my super cool message." \
        --use-param-defaults
