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

    kapt "com.flownav:processor:$flownav_version"
}
```

#### Annotation

```groovy
dependencies {

    implementation "com.flownav:annotation:$flownav_version"
}
```

#### Router

```groovy
dependencies {

    api "com.flownav:router:$flownav_version"
}
```

#### Fragment Router

```groovy
dependencies {

    api "com.flownav:routerfragment:$flownav_version"
}
```

>**`Router and Fragment Router` dependency is imported only on `navigation module`**.
>
>**The `app module` only need the `Annotation` dependency**
>
>**All `feature-module` will import the `Processor` and `Annotation` dependencies**

# Activity Usage

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

# Fragment Usage

> The FlowNav library for now only support work with fragment when used with Navigation Component

To work with fragment is similiar to acitvity, we need to annotate our fragment with `EntryFragmentFlowNav`, the annotation receive two parameters, the first is the constant to identify the destination of the fragment and the second is an name of the resource ID for the fragment, the resource ID should be created on `navigation module`. 

>`@EntryFragmentFlowNav(DESTINATION_NAME, FRAGMENT_ID)`

Add `@EntryFragmentFlowNav` annotation on fragment

```kotlin
@EntryFragmentFlowNav(DESTINATION_NAME, FRAGMENT_ID)
class SampleFragment: Fragment() { ...
```

> The `FRAGMENT_ID` should be a string with the name of the resource ID that you created, see the sample project for more details


## Create your Fragment Router class

On navigation module, create your router extending  `FlowNavFragmentRouter` that router is responsable to export navigation`s functions.

>`object NavigationFragmentRouter : FlowNavFragmentRouter() { ...`

The FlowNav expose an extension function called `workWithNavGraphOf` for `FlowNavFragmentRouter`, this function receive the `navHost resource id`, an `activity` and a DSL for `FlowNavFragmentRouter's functions`.

The `workWithNavGraphOf` will add new destinations on `navHost graph` or if the `navHost` don't have a `navGraph` he will create a `navGraph` a nd inflate on `navHost`

The `FlowNavFragmentRouter` expose an function called `addDestination`, this function receive the flag `isStartDestination` and the `destinationKey`.

You will use the `addDestination` function when you need create an new destination for the navGraph, the flag `isStartDestination` is to inform if the destination is the startDestination of the navGraph, the `destinationKey` is the constant that you used on `@EntryFragmentFlowNav` annotation to identify the destination of the fragment.

```kotlin
object NavigationFragmentRouter : FlowNavFragmentRouter() {

    fun initNavGraphOn(activity: FragmentActivity, @IdRes navHost: Int) {
        workWithNavGraphOf(navHost, activity) {
            addDestination(
                isStartDestination = true,
                destinationKey = DESTINATION_NAME
            )
        }
    }
}
```

If you need to create an action for the destination you will use `withActions` infix fun, the function is an HashMap where you will pass the fragment of the origin and the fragment destination, in both you will pass the constant that you used on `@EntryFragmentFlowNav` annotation to identify the destination of the fragment 

```kotlin
addDestination(...)?. withActions {
        put(DESTINATION_NAME_FRAGMENT_ONE, DESTINATION_NAME_FRAGMENT_TWO)
 }
```

You can pass as many destinations as you wish 

```kotlin
...

addDestination(
    ...
)
addDestination(
    ...
)
addDestination(
    ...
)
```

### Using NavigationFragmentRouter initNavGraphOn function on navHost Activity

```kotlin
@EntryFlowNav(NavigationRoutes.FEATURE_3)
class ActivityStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initNavGraphOn(this, R.id.navhostMain)
    }
}

```

To navigate beteween the fragments we need to use a extension function for `FlowNavFragmentRouter` called `navigateTo` who receive the constant that you used on `@EntryFragmentFlowNav` annotation to identify the destination of the fragment and a lifecycleOwner.

```kotlin
object NavigationFragmentRouter : FlowNavFragmentRouter() {
    ...

    fun navigateToDestinationFragment(lifecycleOwner: LifecycleOwner) {
        navigateTo(DESTINATION_NAME, lifecycleOwner)
    }
}
```

```kotlin
class OriginFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ...

        buttonGoToDestination.setOnClickListener {
            NavigationFragmentRouter.navigateToDestinationFragment(this)
        }
    }
}

```


## Initialize NavigationFragmentRouter on Application

To initialize the NavigationFragmentRouter library we need put the `@FlowNavMain` annotation on our Application class in app module and initialize our Router.

>The `@FlowNavMain` serve to identify the Application class to the library
>
>To initialize our Router we will call the `init` function from `FlowNavFragmentRouter` who will receiver `FlowNavActions` who is a generated class that provides the className and id of each fragment who is annotated with `@EntryFragmentFlowNav`

```kotlin
@FlowNavMain
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NavigationFragmentRouter.init(FlowNavActions.getFragments())
    }
}
```

## LICENSE

This project is available under Apache Public License version 2.0. See [LICENSE](LICENSE.md).
