import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Contact(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val email: String
)

object ContactManager {
    private var contactMap by mutableStateOf(mutableMapOf<Long, Contact>())
    private var nextId = 1L

    fun getContacts(): List<Contact> {
        return contactMap.values.toList()
    }

    fun getContactById(id: Long?): Contact? {
        return contactMap[id]
    }

    fun addContact(newContact: Contact) {
        val contactWithId = newContact.copy(id = nextId)
        contactMap = contactMap.toMutableMap().apply {
            put(contactWithId.id, contactWithId)
        }
        nextId++
    }

    fun updateContact(updatedContact: Contact) {
        contactMap = contactMap.toMutableMap().apply {
            put(updatedContact.id, updatedContact)
        }
    }

    fun isAddressValid(address: String): Boolean {
        return address.trim().split(Regex("\\s+")).size >= 5
    }
}