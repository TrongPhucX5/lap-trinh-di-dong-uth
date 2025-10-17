package com.example.uicomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.uicomponents.ui.theme.UIComponentsTheme
import kotlinx.coroutines.delay

object Routes {
    const val WELCOME = "welcome"
    const val COMPONENT_LIST = "component_list"
    const val TEXT_DETAIL = "text_detail"
    const val IMAGE_DETAIL = "image_detail"
    const val TEXTFIELD_DETAIL = "textfield_detail"
    const val ROW_DETAIL = "row_detail"
    const val COLUMN_DETAIL = "column_detail"
    const val BOX_DETAIL = "box_detail"
    const val INTERACTIVE_DETAIL = "interactive_detail"
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIComponentsTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) { WelcomeScreen(navController = navController) }
        composable(Routes.COMPONENT_LIST) { ComponentListScreen(navController = navController) }
        composable(Routes.TEXT_DETAIL) { TextDetailScreen(navController = navController) }
        composable(Routes.IMAGE_DETAIL) { ImageDetailScreen(navController = navController) }
        composable(Routes.TEXTFIELD_DETAIL) { TextFieldDetailScreen(navController = navController) }
        composable(Routes.ROW_DETAIL) { RowLayoutScreen(navController = navController) }
        composable(Routes.COLUMN_DETAIL) { ColumnLayoutScreen(navController = navController) }
        composable(Routes.BOX_DETAIL) { BoxDetailScreen(navController = navController) }
        composable(Routes.INTERACTIVE_DETAIL) { InteractiveComponentsScreen(navController = navController) }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.jetpack_logo),
                contentDescription = "Logo ứng dụng",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Lê Trọng Phúc", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "089205021215", fontSize = 18.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Jetpack Compose is a modern toolkit for building native Android applications using a declarative programming approach.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate("component_list")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "I'm ready")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentListScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("UI Components List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Bạn có thể thêm hành động ở đây */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        val components = mapOf(
            "Text" to Routes.TEXT_DETAIL,
            "Image" to Routes.IMAGE_DETAIL,
            "TextField" to Routes.TEXTFIELD_DETAIL,
            "Column" to Routes.COLUMN_DETAIL,
            "Row" to Routes.ROW_DETAIL,
            "Box" to Routes.BOX_DETAIL,
            "Interactive Components" to Routes.INTERACTIVE_DETAIL,
        )

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(components.entries.toList()) { (title, route) ->
                ComponentListItem(
                    title = title,
                    subtitle = "View details for $title",
                    onClick = { navController.navigate(route) }
                )
            }
        }
    }
}

@Composable
fun ComponentListItem(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun TextDetailScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar("Text Detail", navController) }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    append("The quick ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)) {
                        append("Brown\n")
                    }
                    append("fox jumps ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.Gray)) {
                        append("over\n")
                    }
                    append("the lazy dog.")
                },
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ImageDetailScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1000)
        isLoading = false
    }

    Scaffold(topBar = { DetailTopAppBar("Images", navController) }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text("Image 1", style = MaterialTheme.typography.titleMedium)
                        Image(
                            painter = painterResource(id = R.drawable.image1),
                            contentDescription = "First image from drawable",
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                    }

                    item {
                        Text("Image 2", style = MaterialTheme.typography.titleMedium)
                        Image(
                            painter = painterResource(id = R.drawable.image2),
                            contentDescription = "Second image from drawable",
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                    }

                    item {
                        Text("Image 3", style = MaterialTheme.typography.titleMedium)
                        Image(
                            painter = painterResource(id = R.drawable.image3),
                            contentDescription = "Third image from drawable",
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldDetailScreen(navController: NavController) {
    var textValue by remember { mutableStateOf("") }

    Scaffold(topBar = { DetailTopAppBar("TextField", navController) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = textValue,
                onValueChange = { newValue ->
                    textValue = newValue
                },
                label = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Bạn đang nhập: $textValue")
        }
    }
}

@Composable
fun RowLayoutScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar("Row Layout", navController) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(4) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier.size(80.dp).background(Color(0xFF8DB0F8), shape = MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ColumnLayoutScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar("Column Layout", navController) }) { innerPadding ->
        Row(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(2) {
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(100.dp).background(Color(0xFF9FF88D), shape = MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BoxDetailScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar("Box Layout", navController) }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(300.dp).background(Color.LightGray, shape = MaterialTheme.shapes.medium))
            Box(modifier = Modifier.size(200.dp).background(Color.Gray, shape = MaterialTheme.shapes.medium))
            Text("Tpzz is one", color = Color.White)
        }
    }
}

@Composable
fun InteractiveComponentsScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar("Interactive Components", navController) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            var isChecked by remember { mutableStateOf(false) }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { isChecked = !isChecked }) {
                Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
                Text("Checkbox")
            }

            var isSwitchOn by remember { mutableStateOf(true) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = isSwitchOn, onCheckedChange = { isSwitchOn = it })
                Spacer(Modifier.width(8.dp))
                Text("Switch")
            }

            val radioOptions = listOf("Option A", "Option B", "Option C")
            var selectedRadio by remember { mutableStateOf(radioOptions[0]) }
            Row {
                radioOptions.forEach { text ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { selectedRadio = text }) {
                        RadioButton(selected = (text == selectedRadio), onClick = { selectedRadio = text })
                        Text(text)
                    }
                }
            }

            var sliderPosition by remember { mutableStateOf(50f) }
            Column {
                Slider(value = sliderPosition, onValueChange = { sliderPosition = it }, valueRange = 0f..100f)
                Text(text = "Slider Value: ${sliderPosition.toInt()}", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}