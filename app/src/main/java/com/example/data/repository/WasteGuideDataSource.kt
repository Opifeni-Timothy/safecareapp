package com.example.data.repository

import com.example.data.model.CommunityDropOffPoint
import com.example.data.model.CommunityReport
import com.example.data.model.CommunityReportType
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.ReportStatus
import com.example.data.model.ScheduleFrequency
import com.example.data.model.WasteCategory
import com.example.data.model.WasteSchedule
import com.example.data.model.WasteSortingItem

object WasteGuideDataSource {

    val initialSchedules: List<WasteSchedule> = listOf(
        WasteSchedule(
            title = "Blue Bin - Recyclables Collection",
            category = WasteCategory.RECYCLABLE.name,
            dayOfWeek = "Tuesday",
            frequency = ScheduleFrequency.WEEKLY.name,
            timeOfDay = "07:00 AM",
            reminderEnabled = true,
            reminderMinutesBefore = 720,
            zoneOrAddress = "Zone 1 - Main Neighborhood",
            instructions = "Rinse all containers. Flatten cardboard boxes and place inside or neatly beside bin.",
            isCompletedThisCycle = false,
            isCommunitySchedule = false
        ),
        WasteSchedule(
            title = "Green Bin - Organics & Food Scraps",
            category = WasteCategory.COMPOST.name,
            dayOfWeek = "Thursday",
            frequency = ScheduleFrequency.WEEKLY.name,
            timeOfDay = "06:30 AM",
            reminderEnabled = true,
            reminderMinutesBefore = 720,
            zoneOrAddress = "Zone 1 - Main Neighborhood",
            instructions = "Line bin with certified compostable paper bag or newspaper. No plastic grocery bags.",
            isCompletedThisCycle = false,
            isCommunitySchedule = false
        ),
        WasteSchedule(
            title = "Black Bin - General Household Waste",
            category = WasteCategory.GENERAL_WASTE.name,
            dayOfWeek = "Friday",
            frequency = ScheduleFrequency.WEEKLY.name,
            timeOfDay = "07:30 AM",
            reminderEnabled = true,
            reminderMinutesBefore = 60,
            zoneOrAddress = "Zone 1 - Main Neighborhood",
            instructions = "Bag all loose non-recyclable trash securely. Ensure bin lid closes flat.",
            isCompletedThisCycle = false,
            isCommunitySchedule = false
        ),
        WasteSchedule(
            title = "Hazardous & E-Waste Mobile Depot",
            category = WasteCategory.HAZARDOUS.name,
            dayOfWeek = "Saturday (1st of month)",
            frequency = ScheduleFrequency.MONTHLY.name,
            timeOfDay = "09:00 AM - 02:00 PM",
            reminderEnabled = true,
            reminderMinutesBefore = 1440,
            zoneOrAddress = "Community Center Parking Lot",
            instructions = "Bring paints, electronics, batteries, fluorescent bulbs in original or clearly labeled containers.",
            isCompletedThisCycle = false,
            isCommunitySchedule = true
        ),
        WasteSchedule(
            title = "Community Bulky Item & Appliance Pickup",
            category = WasteCategory.SPECIAL_DROP_OFF.name,
            dayOfWeek = "Wednesday (Last of month)",
            frequency = ScheduleFrequency.MONTHLY.name,
            timeOfDay = "08:00 AM",
            reminderEnabled = false,
            reminderMinutesBefore = 1440,
            zoneOrAddress = "Curbside Special Collection",
            instructions = "Limit of 2 items (mattresses, desks, scrap metal). Schedule in advance with municipal desk.",
            isCompletedThisCycle = false,
            isCommunitySchedule = true
        )
    )

    val sortingItems: List<WasteSortingItem> = listOf(
        WasteSortingItem(
            id = "pet_plastic_bottles",
            name = "Plastic Water & Soda Bottles",
            category = WasteCategory.RECYCLABLE,
            subcategory = "Plastics",
            recyclingCode = "#1 PET / PETE",
            preparationSteps = listOf(
                "Empty liquid completely and rinse with cold water.",
                "Replace screw cap onto bottle before recycling (keeps caps from jamming machinery).",
                "Crush gently to save bin space."
            ),
            commonMistakes = listOf(
                "Leaving half-full liquid inside (contaminates bales).",
                "Throwing loose unattached bottle caps (they fall through sorting screens)."
            ),
            disposalGuideline = "Place directly in the Blue Curbside Recycling Bin.",
            environmentalImpact = "Recycling 1 plastic bottle saves enough energy to power a 60W lightbulb for 3 hours!",
            isPopular = true
        ),
        WasteSortingItem(
            id = "pizza_box",
            name = "Pizza Box (Greasy vs Clean)",
            category = WasteCategory.COMPOST,
            subcategory = "Paper & Cardboard",
            recyclingCode = "Mixed Cardboard",
            preparationSteps = listOf(
                "Tear box into two halves along the hinge.",
                "Place clean top cardboard in Blue Recycling Bin.",
                "Place grease-soaked bottom and liner paper in Green Compost Bin."
            ),
            commonMistakes = listOf(
                "Putting heavily oil-soaked cardboard into paper recycling (oil destroys the paper pulping cycle)."
            ),
            disposalGuideline = "Greasy section -> Green Compost Bin. Clean dry section -> Blue Recycling Bin.",
            environmentalImpact = "Composting greasy cardboard converts stubborn cellulose into nutrient-rich soil humus within 8-12 weeks.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "lithium_batteries",
            name = "Rechargeable & Lithium-Ion Batteries",
            category = WasteCategory.HAZARDOUS,
            subcategory = "E-Waste / Hazardous",
            recyclingCode = "Hazardous Class 9",
            preparationSteps = listOf(
                "Apply clear adhesive tape over the metallic terminals (+ and - ends).",
                "Keep in a dry, room-temperature container.",
                "Never puncture, crush, or place in standard home bins."
            ),
            commonMistakes = listOf(
                "Tossing into curbside trash or blue bin (major fire hazard in collection trucks and recycling sorting plants!)."
            ),
            disposalGuideline = "Take to Community Battery Drop-off Depot, hardware store collection kiosk, or Safe Care Hub.",
            environmentalImpact = "Recovers rare cobalt, nickel, and lithium while preventing heavy metals from leaching into groundwater.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "aluminum_cans",
            name = "Aluminum Beverage & Soda Cans",
            category = WasteCategory.RECYCLABLE,
            subcategory = "Metals",
            recyclingCode = "#41 ALU",
            preparationSteps = listOf(
                "Empty any beverage residue.",
                "Quick rinse is helpful.",
                "Do not need to remove tab."
            ),
            commonMistakes = listOf(
                "Throwing foil-lined plastic pouches with metal cans."
            ),
            disposalGuideline = "Place in Blue Recycling Bin.",
            environmentalImpact = "Recycling aluminum uses 95% less energy than mining raw bauxite ore and can be back on shelves in 60 days!",
            isPopular = true
        ),
        WasteSortingItem(
            id = "fruit_vegetable_scraps",
            name = "Fruit, Vegetable & Food Scraps",
            category = WasteCategory.COMPOST,
            subcategory = "Organic Waste",
            recyclingCode = "100% Organic",
            preparationSteps = listOf(
                "Remove plastic stickers, rubber bands, and twist ties from produce.",
                "Place in kitchen caddy lined with compostable paper or certified BPI bag.",
                "Empty into green bin regularly."
            ),
            commonMistakes = listOf(
                "Including regular petroleum plastic produce bags (they ruin commercial composting batches)."
            ),
            disposalGuideline = "Green Organics Bin or backyard home compost heap.",
            environmentalImpact = "Diverting organics prevents methane generation in landfills, a greenhouse gas 28x more potent than CO2.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "coffee_grounds",
            name = "Coffee Grounds & Paper Filters",
            category = WasteCategory.COMPOST,
            subcategory = "Organic Waste",
            recyclingCode = "Organic Compost",
            preparationSteps = listOf(
                "Unbleached paper coffee filters can go straight with grounds into compost.",
                "Remove plastic K-cups or metal Nespresso pods (these need dedicated pod recycling)."
            ),
            commonMistakes = listOf(
                "Throwing single-use non-compostable plastic pods into the green bin."
            ),
            disposalGuideline = "Green Organics Bin or garden soil amendment.",
            environmentalImpact = "Rich in nitrogen and potassium; improves garden soil aeration and moisture retention naturally.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "glass_jars_bottles",
            name = "Glass Jars & Beverage Bottles",
            category = WasteCategory.RECYCLABLE,
            subcategory = "Glass",
            recyclingCode = "#70-#72 GL",
            preparationSteps = listOf(
                "Rinse out food sauces or oils.",
                "Metal lids can be recycled separately in the metal bin.",
                "No need to scrape off paper labels."
            ),
            commonMistakes = listOf(
                "Mixing with broken drinking glasses, Pyrex, window glass, or ceramic mugs (different melting points)."
            ),
            disposalGuideline = "Blue Recycling Bin (or dedicated community glass bottle depot).",
            environmentalImpact = "Glass is 100% recyclable infinitely without loss of purity or quality.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "styrofoam_packaging",
            name = "Styrofoam / Expanded Polystyrene",
            category = WasteCategory.GENERAL_WASTE,
            subcategory = "Plastics",
            recyclingCode = "#6 PS",
            preparationSteps = listOf(
                "Break large foam blocks down if needed for bag fitting.",
                "Ensure no loose foam peanuts fly out in wind."
            ),
            commonMistakes = listOf(
                "Putting foam takeout containers in blue bins (most municipal sorting plants cannot process expanded foam)."
            ),
            disposalGuideline = "Black / Grey Landfill Bin (unless a specialized local EPS densifier drop-off is available).",
            environmentalImpact = "Polystyrene takes 500+ years to degrade. Switch to compostable mycelium or molded paper packaging when possible.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "fluorescent_tubes_cfl",
            name = "Fluorescent Tubes & CFL Bulbs",
            category = WasteCategory.HAZARDOUS,
            subcategory = "Hazardous Waste",
            recyclingCode = "Hazardous / Mercury",
            preparationSteps = listOf(
                "Keep intact; wrap carefully in cardboard/bubble wrap to prevent breakage.",
                "If broken, air out room for 15 mins, scoop with cardboard (do not vacuum)."
            ),
            commonMistakes = listOf(
                "Throwing into household trash where compaction crushing releases toxic mercury vapor into neighborhood air."
            ),
            disposalGuideline = "Community Hazardous Waste Station or participating hardware retail drop-off.",
            environmentalImpact = "Prevents toxic mercury contamination in local aquifers and recovers aluminum and glass.",
            isPopular = false
        ),
        WasteSortingItem(
            id = "cardboard_boxes",
            name = "Corrugated Shipping & Cardboard Boxes",
            category = WasteCategory.RECYCLABLE,
            subcategory = "Paper & Cardboard",
            recyclingCode = "#20 PAP",
            preparationSteps = listOf(
                "Remove plastic bubble mailers and heavy styrofoam inserts.",
                "Flatten and break down all boxes completely.",
                "Keep dry (wet cardboard loses fiber strength)."
            ),
            commonMistakes = listOf(
                "Leaving large unflattened boxes next to bins on windy or rainy days."
            ),
            disposalGuideline = "Blue Recycling Bin (flattened bundles).",
            environmentalImpact = "Recycling 1 ton of cardboard saves 17 trees, 7,000 gallons of water, and 4,000 kW of electricity.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "expired_medicines",
            name = "Expired Medicines & Blister Packs",
            category = WasteCategory.HAZARDOUS,
            subcategory = "Pharmaceuticals",
            recyclingCode = "Bio-Hazardous",
            preparationSteps = listOf(
                "Keep tablets in original packaging or sealed plastic bag.",
                "Black out personal patient information on labels with a permanent marker."
            ),
            commonMistakes = listOf(
                "Flushing pills down the toilet or sink (antibiotics and hormones bypass water treatment and harm aquatic life).",
                "Throwing in regular trash where pets or wildlife could ingest."
            ),
            disposalGuideline = "Pharmacy Take-Back Box or Community Hazardous Waste Depot.",
            environmentalImpact = "Protects municipal drinking water reserves from pharmaceutical contamination.",
            isPopular = false
        ),
        WasteSortingItem(
            id = "single_use_coffee_cups",
            name = "Disposable Coffee Cups (Paper with Plastic Lining)",
            category = WasteCategory.GENERAL_WASTE,
            subcategory = "Mixed Packaging",
            recyclingCode = "Composite",
            preparationSteps = listOf(
                "Separate plastic lid (#5) -> Recyclable in some blue bins.",
                "Cardboard sleeve -> Recyclable (Blue Bin).",
                "Cup itself -> General Waste (due to bonded polyethylene waterproof lining)."
            ),
            commonMistakes = listOf(
                "Assuming paper coffee cups are recyclable (the thin plastic interior membrane clogs paper mills)."
            ),
            disposalGuideline = "Lid & Sleeve to Blue Bin; Cup to Black Trash Bin (or use a reusable thermos!).",
            environmentalImpact = "Over 16 billion single-use coffee cups are discarded globally every year. Reusable mugs eliminate this completely.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "old_clothing_textiles",
            name = "Old Clothing, Shoes & Household Textiles",
            category = WasteCategory.SPECIAL_DROP_OFF,
            subcategory = "Textiles",
            recyclingCode = "Textile",
            preparationSteps = listOf(
                "Wash and dry clean clothes.",
                "Tie shoes together in pairs.",
                "Place in waterproof plastic donation bag."
            ),
            commonMistakes = listOf(
                "Throwing clean clothes into curbside general waste where they rot in landfill."
            ),
            disposalGuideline = "Community Textile Donation Bank, Charity Shop, or Brand Garment Recycling program.",
            environmentalImpact = "Extends fabric lifecycle; unwearable textiles are shredded for automotive insulation and acoustic padding.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "used_cooking_oil",
            name = "Used Cooking Oil & Grease",
            category = WasteCategory.SPECIAL_DROP_OFF,
            subcategory = "Oils & Liquids",
            recyclingCode = "Bio-Fuel Feedstock",
            preparationSteps = listOf(
                "Allow cooking oil to cool completely.",
                "Strain food crumbs and pour into a clean plastic bottle with a screw-tight cap.",
                "Do not mix with water or motor oil."
            ),
            commonMistakes = listOf(
                "Pouring hot grease down kitchen drains (causes 'fatbergs' and pipe blockages costing municipalities millions)."
            ),
            disposalGuideline = "Community Biofuel Collection Station or solid waste drop-off.",
            environmentalImpact = "Refined into clean-burning biodiesel, reducing greenhouse emissions by up to 85% compared to petroleum diesel.",
            isPopular = false
        ),
        WasteSortingItem(
            id = "electronics_cables",
            name = "Old Phones, Chargers & Broken Cables",
            category = WasteCategory.HAZARDOUS,
            subcategory = "E-Waste",
            recyclingCode = "WEEE Directive",
            preparationSteps = listOf(
                "Factory reset and wipe personal data from phones/computers.",
                "Bundle loose cords with a rubber band or twist tie.",
                "Separate removable batteries if easy."
            ),
            commonMistakes = listOf(
                "Putting in household trash bin or curbside recycling (wires tangle sorting machines)."
            ),
            disposalGuideline = "Community E-Waste Bin, electronics store drop-box, or Safe Care Hub.",
            environmentalImpact = "Recovers gold, copper, silver, and palladium while sequestering lead and brominated flame retardants.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "plastic_bags_film",
            name = "Plastic Grocery Bags & Bubble Wrap",
            category = WasteCategory.SPECIAL_DROP_OFF,
            subcategory = "Soft Plastics",
            recyclingCode = "#4 LDPE",
            preparationSteps = listOf(
                "Ensure bags are dry and clean (shake out crumbs).",
                "Stuff multiple plastic bags inside one single bag to form a tight ball."
            ),
            commonMistakes = listOf(
                "Putting loose plastic bags in the curbside blue bin (they tangle spinning sorting wheels and shut down plants for hours!)."
            ),
            disposalGuideline = "Supermarket Front-of-Store Plastic Film Bin or municipal soft plastic drop-off.",
            environmentalImpact = "Reprocessed into weather-resistant composite decking lumber and industrial asphalt additives.",
            isPopular = true
        ),
        WasteSortingItem(
            id = "paint_cans_solvents",
            name = "Leftover House Paint & Thinners",
            category = WasteCategory.HAZARDOUS,
            subcategory = "Chemicals",
            recyclingCode = "VOC Hazardous",
            preparationSteps = listOf(
                "Ensure lid is hammered securely shut.",
                "If only a spoonful of latex paint remains, let it dry hard with lid off, then dispose in trash.",
                "Oil-based paints must always go to hazardous depot."
            ),
            commonMistakes = listOf(
                "Pouring paint down stormwater storm drains or onto soil."
            ),
            disposalGuideline = "Community Household Hazardous Waste (HHW) depot.",
            environmentalImpact = "Latex paints are remixed into recycled industrial primers; avoids toxic VOC vapor dispersion.",
            isPopular = false
        ),
        WasteSortingItem(
            id = "yard_garden_waste",
            name = "Lawn Clippings, Branches & Leaves",
            category = WasteCategory.COMPOST,
            subcategory = "Yard Organics",
            recyclingCode = "Green Waste",
            preparationSteps = listOf(
                "Cut thick branches into pieces under 3 feet (1 meter) long.",
                "Bundle with natural biodegradable twine if overflowing bin.",
                "No rocks, treated lumber, or metal wires."
            ),
            commonMistakes = listOf(
                "Mixing plastic flower pots or synthetic soil bags into yard waste."
            ),
            disposalGuideline = "Green Yard Waste Bin or community composting center.",
            environmentalImpact = "Converted into high-grade mulch and organic compost returned free to community parks and city gardens.",
            isPopular = true
        )
    )

    val dropOffPoints: List<CommunityDropOffPoint> = listOf(
        CommunityDropOffPoint(
            id = "hub_1",
            name = "Central Municipal Eco-Depot",
            category = WasteCategory.SPECIAL_DROP_OFF,
            address = "450 Eco Parkway, Greenfield",
            neighborhood = "Central District",
            distanceKm = 1.8,
            operatingHours = "Mon - Sat: 8:00 AM - 5:00 PM | Sun: 9:00 AM - 2:00 PM",
            acceptedItems = listOf("Bulk Furniture", "Scrap Metal", "Electronics & Appliances", "Hazardous Chemicals", "Car Batteries", "Latex & Oil Paint"),
            contactInfo = "(555) 019-3820",
            isOpenNow = true,
            tips = "Free for residents with municipal ID card. Drive-through bay available."
        ),
        CommunityDropOffPoint(
            id = "hub_2",
            name = "Metro E-Waste & Battery Recycling Hub",
            category = WasteCategory.HAZARDOUS,
            address = "120 Tech Drive, Suite 10",
            neighborhood = "North District",
            distanceKm = 3.2,
            operatingHours = "Daily: 9:00 AM - 7:00 PM",
            acceptedItems = listOf("Laptops & Computers", "Lithium & Alkaline Batteries", "Smartphones & Tablets", "Cables & Power Adapters", "TVs & Monitors"),
            contactInfo = "(555) 018-9944",
            isOpenNow = true,
            tips = "Offers secure digital data sanitization certification on-site."
        ),
        CommunityDropOffPoint(
            id = "hub_3",
            name = "Oakwood Community Compost Station",
            category = WasteCategory.COMPOST,
            address = "78 Garden Lane (Behind Community Center)",
            neighborhood = "Oakwood West",
            distanceKm = 0.9,
            operatingHours = "Wed & Sat: 7:00 AM - 1:00 PM",
            acceptedItems = listOf("Kitchen Food Scraps", "Coffee Grounds", "Dry Leaves & Garden Clippings", "Raw Fruit & Veggie Peels"),
            contactInfo = "(555) 014-7221",
            isOpenNow = true,
            tips = "Bring your food scraps and take home free matured organic compost for your house plants!"
        ),
        CommunityDropOffPoint(
            id = "hub_4",
            name = "GreenThread Textile & Garment Donation Box",
            category = WasteCategory.SPECIAL_DROP_OFF,
            address = "320 Market Street Plaza",
            neighborhood = "Downtown East",
            distanceKm = 2.4,
            operatingHours = "24/7 Drop Box Kiosk",
            acceptedItems = listOf("Wearable Clothing", "Worn/Torn Textiles for Shredding", "Pairs of Shoes", "Bedsheets & Towels", "Curtains"),
            contactInfo = "(555) 012-4488",
            isOpenNow = true,
            tips = "Please ensure all items are placed securely inside sealed waterproof bags."
        ),
        CommunityDropOffPoint(
            id = "hub_5",
            name = "Highland Hazardous Chemical Depot",
            category = WasteCategory.HAZARDOUS,
            address = "89 Industrial Way, Gate 3",
            neighborhood = "Highland Industrial Park",
            distanceKm = 5.6,
            operatingHours = "Tues, Thurs, Sat: 8:30 AM - 4:00 PM",
            acceptedItems = listOf("Pesticides & Fertilizers", "Solvents & Cleaners", "Motor Oil & Antifreeze", "Propane Tanks", "Mercury Thermometers"),
            contactInfo = "(555) 017-6033",
            isOpenNow = true,
            tips = "Keep containers in upright boxes lined with plastic sheet during transport."
        )
    )

    val sampleReports: List<CommunityReport> = listOf(
        CommunityReport(
            title = "Overflowing Recyclable Bin at Maple Park",
            description = "The public blue bottle bin at the main entrance of Maple Park is overflowing onto the grass after the weekend soccer game.",
            reportType = CommunityReportType.OVERFLOWING_BIN.name,
            location = "Maple Park Main Entrance, Greenfield",
            neighborhood = "Maple Park",
            timestamp = System.currentTimeMillis() - 3600000 * 4,
            status = ReportStatus.IN_PROGRESS.name,
            upvotes = 7,
            reporterName = "Sarah K."
        ),
        CommunityReport(
            title = "Illegal Bulk Tire & Mattress Dumping on Elm St Alley",
            description = "Observed 4 discarded tires and an old sofa left in the service lane behind 140 Elm Street.",
            reportType = CommunityReportType.ILLEGAL_DUMPING.name,
            location = "Rear service alley, 140 Elm Street",
            neighborhood = "North District",
            timestamp = System.currentTimeMillis() - 3600000 * 18,
            status = ReportStatus.REPORTED.name,
            upvotes = 12,
            reporterName = "David L."
        ),
        CommunityReport(
            title = "Cracked Green Bin Lid - Corner of 4th & Pine",
            description = "Public organics collection container has a broken hinge and cannot stay shut against rain and squirrels.",
            reportType = CommunityReportType.DAMAGED_BIN.name,
            location = "Corner of 4th Ave & Pine Street",
            neighborhood = "Downtown East",
            timestamp = System.currentTimeMillis() - 3600000 * 48,
            status = ReportStatus.RESOLVED.name,
            upvotes = 5,
            reporterName = "Elena M."
        )
    )

    val sampleWasteLogs: List<HouseholdWasteLog> = listOf(
        HouseholdWasteLog(
            dateTimestamp = System.currentTimeMillis() - 86400000 * 1,
            category = WasteCategory.RECYCLABLE.name,
            amountKg = 3.2,
            itemsDivertedCount = 14,
            notes = "Cleaned glass jars, cardboard cereal boxes, and crushed aluminum seltzer cans."
        ),
        HouseholdWasteLog(
            dateTimestamp = System.currentTimeMillis() - 86400000 * 2,
            category = WasteCategory.COMPOST.name,
            amountKg = 2.4,
            itemsDivertedCount = 8,
            notes = "Vegetable prep scraps, coffee grounds, and backyard trimmed rose branches."
        ),
        HouseholdWasteLog(
            dateTimestamp = System.currentTimeMillis() - 86400000 * 4,
            category = WasteCategory.HAZARDOUS.name,
            amountKg = 0.8,
            itemsDivertedCount = 6,
            notes = "Taped 6 spent AA and AAA batteries and stored in safe drop-off pouch."
        ),
        HouseholdWasteLog(
            dateTimestamp = System.currentTimeMillis() - 86400000 * 6,
            category = WasteCategory.GENERAL_WASTE.name,
            amountKg = 1.5,
            itemsDivertedCount = 4,
            notes = "Cleaned out non-recyclable wrappers and broken ceramic flowerpot."
        )
    )

    data class QuizQuestion(
        val question: String,
        val options: List<String>,
        val correctIndex: Int,
        val explanation: String
    )

    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            question = "Should greasy pizza box bottoms go into the blue paper recycling bin?",
            options = listOf(
                "Yes, as long as the crusts are removed",
                "No, food oil contaminates paper pulping; put greasy cardboard in Compost (Green Bin)",
                "Yes, modern mills wash oil out easily",
                "No, it must always go to hazardous chemical landfill"
            ),
            correctIndex = 1,
            explanation = "Oil and grease cannot be separated from paper fibers during pulping. Greasy cardboard belongs in your organics/compost bin!"
        ),
        QuizQuestion(
            question = "What is the correct way to recycle plastic bottles with screw-on caps?",
            options = listOf(
                "Throw caps in the trash and bottles in recycling",
                "Rinse the bottle, crush lightly, and screw the cap back on",
                "Cut the bottle in half and discard the neck",
                "Bury the cap in garden soil"
            ),
            correctIndex = 1,
            explanation = "Modern sorting facilities prefer caps screwed back on rinsed bottles so small caps don't fall through sorting screens."
        ),
        QuizQuestion(
            question = "Why should loose plastic grocery bags NOT go into curbside blue bins?",
            options = listOf(
                "They are too expensive to melt",
                "They wrap around and jam spinning sorting gears in recycling plants",
                "They turn into liquid immediately",
                "They emit toxic gas when touched"
            ),
            correctIndex = 1,
            explanation = "Soft plastic bags tangle machinery, forcing entire plants to halt. Return them to grocery store plastic film drop-off kiosks instead."
        ),
        QuizQuestion(
            question = "Where should old alkaline and lithium batteries be safely disposed?",
            options = listOf(
                "General household garbage bin",
                "Blue curbside recycling bin",
                "Dedicated Battery Drop-Off Depot / Hazardous Waste Depot with taped terminals",
                "Kitchen sink disposal"
            ),
            correctIndex = 2,
            explanation = "Batteries in standard trucks cause severe fires and leak heavy metals. Tape their terminals and drop them at specialized battery kiosks."
        ),
        QuizQuestion(
            question = "What should you do with leftover prescription medicine?",
            options = listOf(
                "Flush them down the toilet",
                "Take them back to a pharmacy drop-off or hazardous waste station",
                "Crush and sprinkle in compost",
                "Mix with milk and pour down the drain"
            ),
            correctIndex = 1,
            explanation = "Flushing medicines contaminates water supplies and harms wildlife. Pharmacies provide safe medicine take-back disposal bins."
        )
    )
}
