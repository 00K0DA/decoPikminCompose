package com.oukoda.decopikmincompose.model.enumclass

import com.oukoda.decopikmincompose.R

enum class DecorType {
    Restaurant,
    Cafe,
    Sweetshop,
    MovieTheater,
    Pharmacy,
    Zoo,
    Forest,
    Waterside,
    PostOffice,
    ArtGallery,
    Airport,
    Station,
    Beach,
    BurgerPlace,
    CornerStore,
    Supermarket,
    Bakery,
    HairSalon,
    ClothesStore,
    Park,
    LibraryAndBookstore,
    Special,
    LoadSide,
    SushiRestaurant,
    Mountain,
    Stadium,
    Weather,
    ThemePark,
    BusStop,
    ItalianRestaurant,
    ;

    fun costumeTypes(): List<CostumeType> = when (this) {
        Restaurant -> listOf(CostumeType.Chef, CostumeType.ShinyChef)
        Cafe -> listOf(CostumeType.CoffeeCup)
        Sweetshop -> listOf(CostumeType.Macaron)
        MovieTheater -> listOf(CostumeType.PopcornSnack)
        Pharmacy -> listOf(CostumeType.Toothbrush)
        Zoo -> listOf(CostumeType.Dandelion)
        Forest -> listOf(CostumeType.StagBeetle, CostumeType.Acorn)
        Waterside -> listOf(CostumeType.FishingLure)
        PostOffice -> listOf(CostumeType.Stamp)
        ArtGallery -> listOf(CostumeType.PictureFrame)
        Airport -> listOf(CostumeType.ToyAirPlane)
        Station -> listOf(CostumeType.PaperTrain, CostumeType.Ticket)
        Beach -> listOf(CostumeType.Shell)
        BurgerPlace -> listOf(CostumeType.Burger)
        CornerStore -> listOf(CostumeType.BottleCap, CostumeType.Snack)
        Supermarket -> listOf(CostumeType.Mushroom, CostumeType.Banana)
        Bakery -> listOf(CostumeType.Baguette)
        HairSalon -> listOf(CostumeType.Scissors)
        ClothesStore -> listOf(CostumeType.HairTie)
        Park -> listOf(CostumeType.Clover, CostumeType.FourLeafClover)
        LibraryAndBookstore -> listOf(CostumeType.TinyBook)
        Special ->
            listOf(
                CostumeType.Mario,
                CostumeType.NewYear,
                CostumeType.NewYear2023,
                CostumeType.Chess,
                CostumeType.FingerBoard,
                CostumeType.FlowerCard,
                CostumeType.JackOLantern,
                CostumeType.FirstAnniversary,
                CostumeType.KoppaiteSpaceSuit,
                CostumeType.Mitten,
                CostumeType.Glasses2023,
                CostumeType.PresentSticker2023,
                CostumeType.Easter,
            )
        LoadSide -> listOf(CostumeType.Sticker, CostumeType.Coin)
        SushiRestaurant -> listOf(CostumeType.Sushi)
        Mountain -> listOf(CostumeType.MountainPinBadge)
        Weather -> listOf(CostumeType.LeafHat)
        ThemePark -> listOf(CostumeType.ThemeParkTicket)
        BusStop -> listOf(CostumeType.BusPaperCraft)
        Stadium -> listOf(CostumeType.BallKeyChain)
        ItalianRestaurant -> listOf(CostumeType.Pizza)
    }

    fun stringId(): Int = when (this) {
        Restaurant -> R.string.decor_type_restaurant
        Cafe -> R.string.decor_type_cafe
        Sweetshop -> R.string.decor_type_sweetshop
        MovieTheater -> R.string.decor_type_movie_theater
        Pharmacy -> R.string.decor_type_pharmacy
        Zoo -> R.string.decor_type_zoo
        Forest -> R.string.decor_type_forest
        Waterside -> R.string.decor_type_waterside
        PostOffice -> R.string.decor_type_post_office
        ArtGallery -> R.string.decor_type_art_gallery
        Airport -> R.string.decor_type_airport
        Station -> R.string.decor_type_station
        Beach -> R.string.decor_type_beach
        BurgerPlace -> R.string.decor_type_burger_place
        CornerStore -> R.string.decor_type_corner_store
        Supermarket -> R.string.decor_type_supermarket
        Bakery -> R.string.decor_type_bakery
        HairSalon -> R.string.decor_type_hair_salon
        ClothesStore -> R.string.decor_type_clothes_store
        Park -> R.string.decor_type_park
        LibraryAndBookstore -> R.string.decor_type_library_and_bookstore
        Special -> R.string.decor_type_special
        LoadSide -> R.string.decor_type_load_side
        SushiRestaurant -> R.string.decor_type_sushi_restaurant
        Mountain -> R.string.decor_type_mountain
        Stadium -> R.string.decor_type_stadium
        Weather -> R.string.decor_type_weather
        ThemePark -> R.string.decor_type_theme_park
        BusStop -> R.string.decor_type_bus_stop
        ItalianRestaurant -> R.string.decor_type_italian_restaurant
    }
}
