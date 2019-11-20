# FlowNav
Android annotation processor and tools for providing better navigation on multi-modules projects.

## Using FlowNav

### Enable Kotlin Kapt
```groovy
apply plugin: 'kotlin-kapt'
```

### Dependencies

#### Processor
```groovy
dependencies {

    kapt 'com.github.jeziellago.FlowNav:processor:0.1'
}
```

#### Annotation
```groovy
dependencies {

    implementation 'com.github.jeziellago.FlowNav:annotation:0.1'
}
```

#### Import `router` only to navigation module
```groovy
dependencies {

    api 'com.github.jeziellago.FlowNav:router:0.1'
}
```

### Add `EntryFlowNav` annotation on feature-modules
```kotlin
@EntryFlowNav(FEATURE_1)
class MyFirstOneActivity : AppCompatActivity() { ...
```

### On navigation module, create your router extending `FlowNavRouter`
```kotlin
object MyNavRouter : FlowNavRouter() {

    fun startFeatureOne(context: Context) = context.open(FEATURE_1)

    fun startFeatureTwo(
        context: Context,
        arg: String
    ) = context.open(FEATURE_2) withParams {
        putString("key", arg)
    }
}
```

### Configure FlowNav on Application
***FlowNavActions is generated class***
```kotlin
@FlowNavMain
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyNavRouter.init(FlowNavActions.get())
    }
}
```
