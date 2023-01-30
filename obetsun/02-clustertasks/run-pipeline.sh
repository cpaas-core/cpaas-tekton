#!/bin/bash

tkn pipeline start cluster-task-pipeline \
    -n default \
    --showlog \
    --workspace name=from-beyond,volumeClaimTemplateFile=pvc.yaml \
    -p message="messsuge" --use-param-defaults
