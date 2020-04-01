# BPM Samples

## Overview

This project contains few samples that demonstrate how to use different components of the BPM addon. The samples are described in the [BPM addon documentation](https://doc.cuba-platform.com/bpm-7.2/examples.html)

The project is based on CUBA Platform 7.2.

## How To Get Started With This Project

1. Import the project using the CUBA Studio
1. Run the project. The models JSONs are located under the [modules/core/web/WEB-INF/resources/models](modules/core/web/WEB-INF/resources/models) directory. They will be automatically deployed by the [ModelDeployer.java](modules/core/src/com/company/bpmsamples/core/bpm/ModelDeployer.java) on application startup.
1. There is a predefined role definition `sample-basic` that gives access to screen and entities provided by the sample. Do not forget to assign required BPM roles (`bpm-process-admin` and `bpm-process-actor`) to your users.