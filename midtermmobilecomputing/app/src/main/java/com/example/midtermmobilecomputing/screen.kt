
sealed class Screen(val route: String) {
    object ListContact : Screen("list_contact_screen")
    object AddEditContact : Screen("add_edit_contact_screen/{contactId}") {
        fun createRoute(contactId: Long) = "add_edit_contact_screen/$contactId"
        fun createRouteForAdd() = "add_edit_contact_screen/0"
    }
}