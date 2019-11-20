# FlowNav &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](https://api.travis-ci.org/jeziellago/FlowNav.svg)

FlowNav is a mobile library for Android that help and provider an better way to make multi-modules navigation.

>**FlowNav is a annotation processor and tool to make navigation simple**.

# Setup

## Current Stable Version

```gradle
// latest stable
flownav_version = '0.1'
```

## Gradle

### Enable Kotlin Kapt

```groovy
apply plugin: 'kotlin-kapt'
```

### Dependencies

#### Processor

```groovy
dependencies {

    kapt "com.github.jeziellago.FlowNav:processor:$flownav_version"
}
```

#### Annotation

```groovy
dependencies {

    implementation "com.github.jeziellago.FlowNav:annotation:$flownav_version"
}
```

#### Router

```groovy
dependencies {

    api "com.github.jeziellago.FlowNav:router:$flownav_version"
}
```

>**`Router` dependency is imported only on `navigation module`**.
>
>**The `app module` only need the `Annotation` dependency**
>
>**All `feature-module` will import the `Processor` and `Annotation` dependencies**

# Usage

## Declare your feature-module's main activity

On Each feature-module on your project you need to add `EntryFlowNav` annotation to infor what activity is the feature-module entry point, the annotation receive an constant to identify the activity on [FlowNavActions](#initialize-flownav-on-application)

>`@EntryFlowNav(ACTIVITY_FEATURE_MODULE_IDENTIFY)`

Add `EntryFlowNav` annotation on feature-modules

```kotlin
@EntryFlowNav(FEATURE_ONE_ACTIVITY_IDENTIFIER)
class MyFirstOneActivity : AppCompatActivity() { ...
```

## Create your Router class

On navigation module, create your router extending  `FlowNavRouter` that router is responsable to export navigation`s functions.

>`object NavRouter: FlowNavRouter() { ...`

Each function need to receive an `Context` and if needed you can pass an `arg` on function`s arguments

>`fun startFeature(context: Context)`
>
>`fun startFeature(context: Context, arg: Any)`

To get the expected intent for the navigation we need to use a context extension function named `open` who expect the [activity identify constant](#declare-your-feature-modules-main-activity)

>`context.open(ACTIVITY_FEATURE_MODULE_IDENTIFY)`

If you need to pass some arguments to intent we will use `withParams` infix fun

>`fun startFeature(context: Context, arg: Any) withParams { ...`

```kotlin
object MyNavRouter : FlowNavRouter() {

    fun startFeatureOne(context: Context) = context.open(FEATURE_ONE_ACTIVITY_IDENTIFIER)

    fun startFeatureTwo(
        context: Context,
        arg: String
    ) = context.open(FEATURE_TWO_ACTIVITY_IDENTIFIER) withParams {
        putString("key", arg)
    }
}
```

## Initialize FlowNav on Application

To initialize the FlowNav library we need put the `@FlowNavMain` annotation on our Application class in app module and initialize our Router.

>The `@FlowNavMain` serve to identify the Application class to the library
>
>To initialize our Router we will call the `init` function from `FlowNavRouter` who will receiver `FlowNavActions` who is a generated class that provides the className of each activity who is annotated with `@EntryFlowNav`

```kotlin
@FlowNavMain
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyNavRouter.init(FlowNavActions.get())
    }
}
```

## LICENSE

This project is available under Apache Public License version 2.0. See [LICENSE](LICENSE.md).
