package com.claudiacabezas.evaluacion1.items

class CuentaMesa {
    private val items = mutableListOf<ItemMesa>()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = items.find { it.itemMenu == itemMenu }
        if (itemMesa != null) {
            itemMesa.cantidad = cantidad
        } else {
            items.add(ItemMesa(itemMenu, cantidad))
        }
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal() }
    }

    private fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}

