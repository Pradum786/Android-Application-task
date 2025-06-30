package com.pradum.inovanttest.models

data class ConfigurableOption(
    val attribute_code: String,
    val attribute_id: Int,
    val attributes: List<Attribute>,
    val type: String
)