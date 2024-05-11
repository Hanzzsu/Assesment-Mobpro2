package org.d3if3062.mobpro1.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0021.asessmen2mobpro.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, viewModel: MainViewModel, id: Long? = null) {

    val radioOptions = listOf(
        stringResource(id = R.string.baju),
        stringResource(id = R.string.celana),
    )

    var nama by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("") }
//    var jenisText by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }



    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id != null) {
            val data = viewModel.getItemById(id)!!
            nama = data.nama
            jenis = data.jenisPakaian
            jumlah = data.jumlahPakaian
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.app_name))
                    else
                        Text(text = stringResource(id = R.string.edit))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (nama.isEmpty()) {
                                Toast.makeText(
                                    navController.context,
                                    R.string.nama_kosong,
                                    Toast.LENGTH_LONG
                                ).show()
                                return@IconButton
                            }

                            if (jenis.isEmpty()) {
                                Toast.makeText(
                                    navController.context,
                                    R.string.jenis_kosong,
                                    Toast.LENGTH_LONG
                                ).show()
                                return@IconButton
                            }

                            if (jumlah.isEmpty()) {
                                Toast.makeText(
                                    navController.context,
                                    R.string.jumlah_kosong,
                                    Toast.LENGTH_LONG
                                ).show()
                                return@IconButton
                            }

                            if (id == null) {
//                                if (jenis == radioOptions[0]) {
//                                    jenisText = "Baju"
//                                }
//                                if (jenis == radioOptions[1]) {
//                                    jenisText = "Celana"
//                                }
                                viewModel.insertData(nama, jenis, jumlah)
                            } else {
                                viewModel.updateData(nama, jenis, jumlah, id)
                            }
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(
                            openDilog = showDialog,
                            onDismissRequest = { showDialog = false },
                        ) {
                            showDialog = false
                            viewModel.deleteDataById(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        DisplayItems(
            nama = nama,
            namaChange = { nama = it },
            jenis = jenis,
            jenisChange = { jenis = it },
            jumlah = jumlah,
            jumlahChange = { jumlah = it },
            radioOptions = radioOptions,
            modifier = Modifier.padding(padding)
        )
    }
}


@Composable
fun DisplayItems(
    nama: String, namaChange: (String) -> Unit,
    jenis: String, jenisChange: (String) -> Unit,
    jumlah: String, jumlahChange: (String) -> Unit,
    radioOptions: List<String> = listOf(),
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { namaChange(it) },
            label = { Text(text = stringResource(id = R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ////////////////////////////////////////////////
        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach {text ->
                RadioItem(
                    label = text,
                    isSelected = jenis == text,
                    modifier = Modifier
                        .selectable(
                            selected = jenis == text,
                            onClick = { jenisChange(text) },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )

            }

        }
        ////////////////////////////////////////////////


        OutlinedTextField(
            value = jumlah,
            onValueChange = { jumlahChange(it) },
            label = { Text(text = stringResource(id = R.string.jumlah)) },
            trailingIcon = {
                Text(text = stringResource(id = R.string.pcs))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus)) },
                onClick = {
                    delete()
                    expanded = false
                }
            )
        }

    }
}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    AboutMeTheme {
//        DetailScreen(rememberNavController(), viewModel = viewModel())
//    }
//}