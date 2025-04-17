package plan.Pages.NewStage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import android.app.TimePickerDialog
import androidx.compose.foundation.interaction.MutableInteractionSource
import plan.common.components.DropdownMenuBox
import plan.common.components.PlusButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewStage(navController: NavHostController) {
    var stageType by remember { mutableStateOf("Airplane") }
    val stageTypes = listOf("Airplane", "Train", "Travel by car")

    var stageName by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("RUB") }
    val currencies = listOf("RUB", "USD", "EUR")
    var links by remember { mutableStateOf(listOf("")) }

    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                selectedTime = String.format("%02d:%02d", hour, minute)
            },
            12, 0, true
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    timePickerDialog.show()
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val selectedDateTime = remember(datePickerState.selectedDateMillis, selectedTime) {
        datePickerState.selectedDateMillis?.takeIf { it > 0 }?.let {
            try {
                val date = Instant.ofEpochMilli(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                "${date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))} $selectedTime"
            } catch (e: Exception) {
                ""
            }
        } ?: ""
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Тип стейджа
        DropdownMenuBox(
            label = "Stage type",
            selected = stageType,
            options = stageTypes,
            onSelect = { stageType = it }
        )

        // Название
        OutlinedTextField(
            value = stageName,
            onValueChange = { stageName = it },
            placeholder = { Text("Stage name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Дата и время с выбором через DatePickerDialog
        OutlinedTextField(
            value = selectedDateTime,
            onValueChange = {},
            placeholder = { Text("Date and time") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    showDatePicker = true
                }
        )

        // Аплоадер (заглушка)
        Text("File uploader (coming soon)", style = MaterialTheme.typography.bodyMedium)

        // Ссылки
        Text("Links")
        links.forEachIndexed { index, link ->
            OutlinedTextField(
                value = link,
                onValueChange = { newValue ->
                    links = links.toMutableList().also { it[index] = newValue }
                },
                placeholder = { Text("Link ${index + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        PlusButton(
            onClick = {
                links = links + ""
            },
            icon = Icons.Default.Add,
            contentDescription = "Add link"
        )

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        // Стоимость и валюта
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it },
                placeholder = { Text("Cost") },
                modifier = Modifier.weight(1f)
            )

            DropdownMenuBox(
                label = "Currency",
                selected = currency,
                options = currencies,
                onSelect = { currency = it },
                modifier = Modifier.weight(1f)
            )
        }
    }
}