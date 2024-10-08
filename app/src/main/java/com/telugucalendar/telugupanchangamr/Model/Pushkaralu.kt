package com.telugucalendar.telugupanchangamr.Model


data class Pushkaralu(
    val id: String,              // Unique ID for the Pushkaralu
    val title: String,           // Title of the Pushkaralu
    val description: String,     // Description of the Pushkaralu
    val pushkaralu_variant: List<PushkaraluVariant>  // List of Pushkaralu variants
)

data class PushkaraluVariant(
    val id: String,              // Unique ID for the Pushkaralu variant
    val pushkaralu_id: String,   // ID linking the variant to a specific Pushkaralu
    val sub_title: String,       // Subtitle for the Pushkaralu variant
    val sub_description: String  // Description for the Pushkaralu variant
)

