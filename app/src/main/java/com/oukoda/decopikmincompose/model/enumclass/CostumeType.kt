package com.oukoda.decopikmincompose.model.enumclass

import com.oukoda.decopikmincompose.R

enum class CostumeType(val value: String) {
    Chef("Chef"),
    ShinyChef("ShinyChef"),
    CoffeeCup("CoffeeCup"),
    Macaron("Macaron"),
    PopcornSnack("PopcornSnack"),
    Toothbrush("Toothbrush"),
    Dandelion("Dandelion"),
    StagBeetle("StagBeetle"),
    Acorn("Acorn"),
    FishingLure("FishingLure"),
    Stamp("Stamp"),
    PictureFrame("PictureFrame"),
    ToyAirPlane("ToyAirPlane"),
    PaperTrain("PaperTrain"),
    Ticket("Ticket"),
    Shell("Shell"),
    Burger("Burger"),
    BottleCap("BottleCap"),
    Snack("Snack"),
    Mushroom("Mushroom"),
    Banana("Banana"),
    Baguette("Baguette"),
    Scissors("Scissors"),
    HairTie("HairTie"),
    Clover("Clover"),
    FourLeafClover("FourLeafClover"),
    TinyBook("TinyBook"),
    Sushi("Sushi"),
    MountainPinBadge("MountainPinBadge"),

    // Weather
    LeafHat("LeafHat"),

    // LoadSide
    Sticker("Sticker"),

    // Bus Stop
    BusPaperCraft("BusPaperCraft"),

    // Special
    Mario("Mario"),
    NewYear("NewYear"),
    Chess("Chess"),
    FingerBoard("FingerBoard"),
    FlowerCard("FlowerCard"),
    JackOLantern("JackOLantern"),
    FirstAnniversary("FirstAnniversary"),

    ThemeParkTicket("ThemeParkTicket"),
    ;

    companion object {
        fun getAllPikminCount(): Int = values().sumOf { it.getPikminList().size }
    }

    fun getPikminList(): List<PikminType> {
        return when (this) {
            Mario -> listOf(PikminType.Red)
            Chess ->
                listOf(PikminType.Yellow, PikminType.Blue, PikminType.White, PikminType.Purple)
            LeafHat -> listOf(PikminType.Blue, PikminType.Blue, PikminType.Blue)
            ShinyChef, NewYear, MountainPinBadge, Sushi, ThemeParkTicket ->
                listOf(PikminType.Red, PikminType.Blue, PikminType.Yellow)
            FingerBoard ->
                listOf(PikminType.Red, PikminType.Yellow, PikminType.Purple, PikminType.Wing)
            FlowerCard ->
                listOf(PikminType.Red, PikminType.Yellow, PikminType.Blue, PikminType.Purple)
            else -> PikminType.values().toList()
        }
    }

    fun getCostumeTextId(): Int {
        return when (this) {
            Chef -> R.string.costume_chef
            ShinyChef -> R.string.costume_shiny_chef
            CoffeeCup -> R.string.costume_coffee_cup
            Macaron -> R.string.costume_macaron
            PopcornSnack -> R.string.costume_popcorn_snack
            Toothbrush -> R.string.costume_toothbrush
            Dandelion -> R.string.costume_dandelion
            StagBeetle -> R.string.costume_stag_beetle
            Acorn -> R.string.costume_acorn
            FishingLure -> R.string.costume_fishing_lure
            Stamp -> R.string.costume_stamp
            PictureFrame -> R.string.costume_picture_frame
            ToyAirPlane -> R.string.costume_toy_air_plane
            PaperTrain -> R.string.costume_paper_train
            Ticket -> R.string.costume_ticket
            Shell -> R.string.costume_shell
            Burger -> R.string.costume_burger
            BottleCap -> R.string.costume_bottle_cap
            Snack -> R.string.costume_snack
            Mushroom -> R.string.costume_mushroom
            Banana -> R.string.costume_banana
            Baguette -> R.string.costume_baguette
            Scissors -> R.string.costume_scissors
            HairTie -> R.string.costume_hair_tie
            Clover -> R.string.costume_clover
            FourLeafClover -> R.string.costume_four_leaf_clover
            TinyBook -> R.string.costume_tiny_book
            Sushi -> R.string.costume_sushi
            MountainPinBadge -> R.string.costume_mountain_pin_badge
            LeafHat -> R.string.costume_leaf_hat
            Sticker -> R.string.costume_sticker
            Mario -> R.string.costume_mario
            NewYear -> R.string.costume_new_year
            Chess -> R.string.costume_chess
            ThemeParkTicket -> R.string.costume_theme_park_ticket
            FingerBoard -> R.string.costume_theme_finger_board
            FlowerCard -> R.string.costume_theme_flower_card
            JackOLantern -> R.string.costume_theme_jack_o_lantern
            BusPaperCraft -> R.string.costume_theme_bus_parer_craft
            FirstAnniversary -> R.string.costume_theme_first_anniversary_snack
        }
    }
}
