package com.oukoda.decopikmincompose.model.enumclass

import androidx.annotation.StringRes
import com.oukoda.decopikmincompose.R

enum class CostumeType(@StringRes val stringId: Int) {
    Chef(R.string.costume_chef),
    ShinyChef(R.string.costume_shiny_chef),
    CoffeeCup(R.string.costume_coffee_cup),
    Macaron(R.string.costume_macaron),
    PopcornSnack(R.string.costume_popcorn_snack),
    Toothbrush(R.string.costume_toothbrush),
    Dandelion(R.string.costume_dandelion),
    StagBeetle(R.string.costume_stag_beetle),
    Acorn(R.string.costume_acorn),
    FishingLure(R.string.costume_fishing_lure),
    Stamp(R.string.costume_stamp),
    PictureFrame(R.string.costume_picture_frame),
    ToyAirPlane(R.string.costume_toy_air_plane),
    PaperTrain(R.string.costume_paper_train),
    Ticket(R.string.costume_ticket),
    Shell(R.string.costume_shell),
    Burger(R.string.costume_burger),
    BottleCap(R.string.costume_bottle_cap),
    Snack(R.string.costume_snack),
    Mushroom(R.string.costume_mushroom),
    Banana(R.string.costume_banana),
    Baguette(R.string.costume_baguette),
    Scissors(R.string.costume_scissors),
    HairTie(R.string.costume_hair_tie),
    Clover(R.string.costume_clover),
    FourLeafClover(R.string.costume_four_leaf_clover),
    TinyBook(R.string.costume_tiny_book),
    Sushi(R.string.costume_sushi),
    MountainPinBadge(R.string.costume_mountain_pin_badge),

    // Stadium
    BallKeyChain(R.string.costume_ball_keychain),

    // Weather
    LeafHat(R.string.costume_leaf_hat),

    // LoadSide
    Sticker(R.string.costume_sticker),
    Coin(R.string.costume_coin),

    // Bus Stop
    BusPaperCraft(R.string.costume_special_bus_parer_craft),

    // Special
    Mario(R.string.costume_special_mario),
    NewYear(R.string.costume_special_new_year),
    Chess(R.string.costume_special_chess),
    FingerBoard(R.string.costume_special_finger_board),
    FlowerCard(R.string.costume_special_flower_card),
    JackOLantern(R.string.costume_special_jack_o_lantern),
    FirstAnniversary(R.string.costume_special_first_anniversary_snack),
    KoppaiteSpaceSuit(R.string.costume_special_koppaite_space_suit),
    Mitten(R.string.costume_special_mitten),
    Glasses2023(R.string.costume_special_2023_glasses),
    NewYear2023(R.string.costume_special_new_year_2023),
    PresentSticker2023(R.string.costume_special_present_sticker_2023),
    Easter(R.string.costume_special_easter),
    ThemeParkTicket(R.string.costume_theme_park_ticket),
    Pizza(R.string.costume_pizza),
    ;

    companion object {
        fun getAllPikminCount(): Int = values().sumOf { it.pikminTypes().size }
    }

    fun pikminTypes(): List<PikminType> {
        return when (this) {
            Mario -> listOf(PikminType.Red)
            Chess ->
                listOf(PikminType.Yellow, PikminType.Blue, PikminType.White, PikminType.Purple)
            LeafHat -> listOf(PikminType.Blue, PikminType.Blue, PikminType.Blue)
            ShinyChef,
            NewYear,
            MountainPinBadge,
            ThemeParkTicket,
            BallKeyChain,
            KoppaiteSpaceSuit,
            Mitten,
            NewYear2023,
            ->
                listOf(PikminType.Red, PikminType.Blue, PikminType.Yellow)
            FingerBoard ->
                listOf(PikminType.Red, PikminType.Yellow, PikminType.Purple, PikminType.Wing)
            FlowerCard ->
                listOf(PikminType.Red, PikminType.Yellow, PikminType.Blue, PikminType.Purple)
            else -> PikminType.values().toList()
        }
    }
}
