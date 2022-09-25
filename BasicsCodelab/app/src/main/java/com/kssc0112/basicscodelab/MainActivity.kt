package com.kssc0112.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kssc0112.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BasicsCodelabTheme {
        MyApp()
      }
    }
  }
}

@Composable
private fun MyApp() {
  var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
  if (shouldShowOnboarding) {
    OnboardingScreen {
      shouldShowOnboarding = false
    }
  } else {
    Greetings()
  }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
  LazyColumn(modifier = Modifier.padding(4.dp)) {
    items(items = names) { name ->
      Greeting(name)
    }
  }
}

@Composable
fun Greeting(name: String) {
  var expanded by remember { mutableStateOf(false) }

  Card(
    backgroundColor = MaterialTheme.colors.primary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(
      modifier = Modifier
        .padding(12.dp)
        .animateContentSize(
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
          )
        )
    ) {
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(12.dp)
      ) {
        Text(text = "Hello, ")
        Text(
          text = name, style = MaterialTheme.typography.h4.copy(
            fontWeight = FontWeight.ExtraBold
          )
        )
        if (expanded) {
          Text(text = stringResource(id = R.string.greeting_text))
        }
      }
      IconButton(
        onClick = { expanded = !expanded }
      ) {
        val imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore
        val contentDescription =
          if (expanded) stringResource(id = R.string.show_less) else stringResource(
            id = R.string.show_more
          )
        Icon(imageVector = imageVector, contentDescription = contentDescription)
      }
    }
  }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
  Surface {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Welcome to the Basics Codelab!")
      Button(
        modifier = Modifier.padding(vertical = 24.dp),
        onClick = onContinueClicked
      ) {
        Text("Continue")
      }
    }
  }
}

@Preview(
  showBackground = true,
  widthDp = 320,
  heightDp = 320,
  uiMode = UI_MODE_NIGHT_YES,
  name = "OnboardingPreviewDark"
)
@Composable
fun OnboardingPreview() {
  BasicsCodelabTheme {
    OnboardingScreen(onContinueClicked = {})
  }
}

@Preview(
  showBackground = true,
  widthDp = 320,
  uiMode = UI_MODE_NIGHT_YES,
  name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
  BasicsCodelabTheme {
    Greetings()
  }
}