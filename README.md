# FlowNav &nbsp;&nbsp; [![](https://androidweekly.net/issues/issue-397/badge)](https://androidweekly.net/issues/issue-397) ![](https://github.com/jeziellago/FlowNav/workflows/DEPLOY/badge.svg?branch=master)

FlowNav is a mobile library for Android that helps and provider a better way to make multi-modules navigation.

>The main purpose of this library is to help in cases where you have a project with multiple modules and need to navigate from one feature to another without adding dependency between them. For example, to navigate from module A to module B, you do not need to add module B as a dependency of module A.
>
>There are other ways to solve the problem of module-to-module navigation such as using Intent-filter (which is error-prone and not support fragments). FlowNav solves these problems, either using Activities, native Fragments, or through the Navigation Component.
>
>**Check the [wiki](https://github.com/jeziellago/FlowNav/wiki).**

![](https://github.com/jeziellago/FlowNav/blob/master/sample/flownav.png)

### Dependency
Add `jitpack.io` as maven repository:
```groovy
allprojects {  
    repositories {  
        ...  
        maven { url 'https://jitpack.io' }  
    }  
}  
```

#### Processor

```groovy
dependencies {

    kapt "dev.jeziellago:flowNav-processor:$VERSION"
}
```

#### Annotation

```groovy
dependencies {

    implementation "dev.jeziellago:flowNav-annotation:$VERSION"
}
```

#### Router

```groovy
dependencies {

    api "dev.jeziellago:flowNav-router:$VERSION"
}
```

>**`Router` dependency is imported only on the `navigation module`**.
>
>**All other modules will import the `Processor` and `Annotation` dependencies**

## Start Now!
* [Import dependencies](https://github.com/jeziellago/FlowNav/wiki/Setup-Dependencies)
* [Init FlowNavApp on Application](https://github.com/jeziellago/FlowNav/wiki/Start-FlowNav)
* [Navigation on Activities](https://github.com/jeziellago/FlowNav/wiki/Navigation-on-Activities)
* [Navigation on Fragments](https://github.com/jeziellago/FlowNav/wiki/Navigation-on-Fragments)
* [Fragments using Navigation Component](https://github.com/jeziellago/FlowNav/wiki/Fragments-using-Navigation-Component)


## LICENSE

This project is available under Apache Public License version 2.0. See [LICENSE](LICENSE.md).
