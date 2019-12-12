# FlowNav &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](https://api.travis-ci.org/jeziellago/FlowNav.svg)

FlowNav is a mobile library for Android that helps and provider a better way to make multi-modules navigation.

>**FlowNav is an annotation processor and tool to make navigation simple**.

# Setup

## Current Stable Version

```gradle
// latest stable
flownav_version = '0.3'
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

>**`Router` dependency is imported only on the `navigation module`**.
>
>**The `app module` only needs `Annotation` dependency**
>
>**All `feature-module` will import the `Processor` and `Annotation` dependencies**

# Activity Usage

## Declare your feature modules main activity

On Each feature-module on your project, you need to add `EntryFlowNav` annotation to inform what activity is the feature-module entry point, the annotation receives a constant to identify the activity on [FlowNavActions](#initialize-flownav-on-application).

>`@EntryFlowNav(ACTIVITY_FEATURE_MODULE_IDENTIFY)`

Add `EntryFlowNav` annotation on feature-modules

```kotlin
@EntryFlowNav(FEATURE_ONE_ACTIVITY_IDENTIFIER)
class MyFirstOneActivity : AppCompatActivity() { ...
```


## Initialize FlowNav on Application

To initialize the FlowNav library, we need to put the `@FlowNavMain` annotation on our Application class in the app module and initialize our Router.

> The `@FlowNavMain` helps to identify the Application class to the library
>
> To initialize our Router, we will call the `start` function from `FlowNavApp`, who will receiver `navMap()` and `navMapFragment()`, extensions that provides the className of each activity annotated with `@EntryFlowNav` and fragments with `@EntryFragmentFlowNav`.

```kotlin
import com.flownav.router.FlowNavApp
import com.flownav.router.navMap
import com.flownav.router.navMapFragment

@FlowNavMain
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FlowNavApp.start(navMap(), navMapFragment())
    }
}
```

## Create your Router class

On the navigation module, create your router extending `FlowNavRouter` that router is responsible to export navigation functions.

>`object NavRouter: FlowNavRouter() { ...`

Each function needs to receive a `Context`, and if needed, you can pass an `arg` on functions arguments.

>`fun startFeature(context: Context)`
>
>`fun startFeature(context: Context, arg: Any)`

To get the expected intent for the navigation, we need to use a context extension function named `start` who expects the [activity identifier constant](#declare-your-feature-modules-main-activity)

>`context.start(ACTIVITY_FEATURE_MODULE_IDENTIFY)`

If you need to pass some arguments to the intent, we will open lambda on second param.


```kotlin
object MyNavRouter : FlowNavRouter() {

    fun startFeatureOne(context: Context) = context.start(FEATURE_ONE_ACTIVITY_IDENTIFIER)

    fun startFeatureTwo(
        context: Context,
        arg: String
    ) = context.start(FEATURE_TWO_ACTIVITY_IDENTIFIER) {
        putString("key", arg)
    }
}
```


# Fragment Usage

> The FlowNav library for now only support work with fragment when used with Navigation Component

Working with a fragment is similar to activity. We need to annotate our fragment with `EntryFragmentFlowNav`, who receives two parameters: the first is a constant to identify the fragment destination, and the second is the name identifier to resource ID for the fragment. The resource ID should be created on the `navigation module`. 

>`@EntryFragmentFlowNav(DESTINATION_NAME, FRAGMENT_ID)`

Add `@EntryFragmentFlowNav` annotation on fragment

```kotlin
@EntryFragmentFlowNav(DESTINATION_NAME, FRAGMENT_ID)
class SampleFragment: Fragment() { ...
```

> The `FRAGMENT_ID` should be a string with the name of the resource ID that you created, see the sample project for more details


## Create your Fragment Router class

On the navigation module, create your router extending  `FlowNavFragmentRouter` that router is used to export navigation functions.

>`object NavigationFragmentRouter : FlowNavFragmentRouter() { ...`

The FlowNav expose an extension function called `workWithNavGraphOf` for `FlowNavFragmentRouter`, this function receive the `navHost resource id`, an `activity` and a DSL for `FlowNavFragmentRouter's functions`.

The `workWithNavGraphOf` will add new destinations on `navHost graph` or if the `navHost` don't have a `navGraph` he will create a `navGraph`  and inflate on `navHost`

The `FlowNavFragmentRouter` expose an function called `addDestination`, this function receive the flag `isStartDestination` and the `destinationKey`.

You will use the `addDestination` function when you need to create a new destination for the navGraph. The flag `isStartDestination` is to inform if the destination is the `startDestination` of the navGraph. The `destinationKey` is a constant that you used on `@EntryFragmentFlowNav` annotation to identify the destination of the fragment.

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

If you need to create an action for the destination you will use `withActions` infix fun, the function is a HashMap where you will pass the fragment of the origin and the fragment destination, in both you will pass the constant that you used on `@EntryFragmentFlowNav` annotation to identify the destination of the fragment.


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

To navigate between the fragments, we need to use an extension function for `FlowNavFragmentRouter` called `navigateTo` who receive the constant that you used on `@EntryFragmentFlowNav` annotation to identify the destination of the fragment and a lifecycleOwner.

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

## LICENSE

This project is available under Apache Public License version 2.0. See [LICENSE](LICENSE.md).
