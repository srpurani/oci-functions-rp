# oci-functions-rp
OCI Function Using Resource Principal

* Install OCI CLI
  https://docs.cloud.oracle.com/iaas/Content/API/SDKDocs/cliinstall.htm
* Install FN CLI
  https://github.com/fnproject/cli
* Setting up repository and network for Oracle Functions
  https://github.com/srpurani/oci-fn-bootstrapper
* Oracle Functions Documentation
  https://docs.cloud.oracle.com/iaas/Content/Functions/Concepts/functionsoverview.htm
  
## Quick Steps - Java Function
* docker login -u "your-tenancy/user-id" - "your-api-key" iad.ocir.io
* setup your fn default profile in ~/.fn/contexts/default.yaml

## Deploying 
* `fn create app --annotation oracle.com/oci/subnetIds='["<demo1-subnet-ocid>"]' demo1`
* `fn build`
* `docker push <image built by above command`
* `fn create function demo1 demo-function <above-image-name> --timeout 120`

## Dynamic Groups and Policies
* Create a dynamic group demo-dg `ALL{resource.type='fnfunc', resource.compartment.id='your-compartment-ocid-where function is created>'}`
* Create a policy `allow dynamic-group demo-dg to inspect users in tenancy`
* Add another statement to policy `allow dynamic-group demo-dg to inspect virtual-network-family in compartment <your compartment>`

## Invoking function
* `echo "your-user-or-vcn-ocid" | DEBUG=1 fn --verbose invoke function demo1 demo-function`

