import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import android.widget.Toast

@Composable
fun AddEditContactScreen(navController: NavController, contactId: Long?) {
    val isEditMode = contactId != null && contactId != 0L
    val existingContact = if (isEditMode) ContactManager.getContactById(contactId) else null
    var name by remember { mutableStateOf(existingContact?.name ?: "") }
    var address by remember { mutableStateOf(existingContact?.address ?: "") }
    var phone by remember { mutableStateOf(existingContact?.phone ?: "") }
    var email by remember { mutableStateOf(existingContact?.email ?: "") }
    var addressError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val title = if (isEditMode) "Edit Contact" else "Add Contact"
    Scaffold(
        topBar = { TopAppBar(title = { Text(title) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Name") }, modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address, onValueChange = { address = it },
                label = { Text("Address (min 5 words)") }, modifier = Modifier.fillMaxWidth(),
                isError = addressError,
                supportingText = {
                    if (addressError) Text("Alamat harus minimal 5 kata.", color = MaterialTheme.colorScheme.error)
                }
            )

            OutlinedTextField(
                value = phone, onValueChange = { phone = it },
                label = { Text("Phone") }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (!ContactManager.isAddressValid(address)) {
                        addressError = true
                        Toast.makeText(context, "Alamat harus minimal 5 kata.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    addressError = false

                    val contactData = Contact(
                        id = existingContact?.id ?: 0L,
                        name = name, address = address, phone = phone, email = email
                    )

                    if (isEditMode && existingContact != null) {
                        ContactManager.updateContact(contactData)
                    } else {
                        ContactManager.addContact(contactData)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Save Contact" else "Add Contact")
            }
        }
    }
}