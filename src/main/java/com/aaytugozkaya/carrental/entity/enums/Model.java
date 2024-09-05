package com.aaytugozkaya.carrental.entity.enums;

public enum Model {
    // Toyota Models
    COROLLA(Brand.TOYOTA),
    CAMRY(Brand.TOYOTA),
    PRIUS(Brand.TOYOTA),
    HIGHLANDER(Brand.TOYOTA),
    RAV4(Brand.TOYOTA),

    // Ford Models
    FOCUS(Brand.FORD),
    FIESTA(Brand.FORD),
    MUSTANG(Brand.FORD),
    EXPLORER(Brand.FORD),
    ESCAPE(Brand.FORD),

    // Honda Models
    CIVIC(Brand.HONDA),
    ACCORD(Brand.HONDA),
    CRV(Brand.HONDA),
    PILOT(Brand.HONDA),
    FIT(Brand.HONDA),

    // Chevrolet Models
    MALIBU(Brand.CHEVROLET),
    IMPALA(Brand.CHEVROLET),
    CAMARO(Brand.CHEVROLET),
    SILVERADO(Brand.CHEVROLET),
    EQUINOX(Brand.CHEVROLET),

    // BMW Models
    SERIES_3(Brand.BMW),
    SERIES_5(Brand.BMW),
    SERIES_7(Brand.BMW),
    X5(Brand.BMW),
    X3(Brand.BMW),

    // Mercedes-Benz Models
    C_CLASS(Brand.MERCEDES_BENZ),
    E_CLASS(Brand.MERCEDES_BENZ),
    S_CLASS(Brand.MERCEDES_BENZ),
    GLE(Brand.MERCEDES_BENZ),
    GLC(Brand.MERCEDES_BENZ),

    // Audi Models
    A3(Brand.AUDI),
    A4(Brand.AUDI),
    A6(Brand.AUDI),
    Q5(Brand.AUDI),
    Q7(Brand.AUDI),

    // Tesla Models
    MODEL_S(Brand.TESLA),
    MODEL_3(Brand.TESLA),
    MODEL_X(Brand.TESLA),
    MODEL_Y(Brand.TESLA),

    // Nissan Models
    ALTIMA(Brand.NISSAN),
    SENTRA(Brand.NISSAN),
    MAXIMA(Brand.NISSAN),
    ROGUE(Brand.NISSAN),
    PATHFINDER(Brand.NISSAN),

    // Volkswagen Models
    GOLF(Brand.VOLKSWAGEN),
    PASSAT(Brand.VOLKSWAGEN),
    JETTA(Brand.VOLKSWAGEN),
    TIGUAN(Brand.VOLKSWAGEN),
    ATLAS(Brand.VOLKSWAGEN),

    // Hyundai Models
    ELANTRA(Brand.HYUNDAI),
    SONATA(Brand.HYUNDAI),
    TUCSON(Brand.HYUNDAI),
    SANTA_FE(Brand.HYUNDAI),
    KONA(Brand.HYUNDAI),
    // Kia Models
    OPTIMA(Brand.KIA),
    SORENTO(Brand.KIA),
    SPORTAGE(Brand.KIA),
    SOUL(Brand.KIA),
    STINGER(Brand.KIA),

    // Mazda Models
    MAZDA3(Brand.MAZDA),
    MAZDA6(Brand.MAZDA),
    CX5(Brand.MAZDA),
    CX9(Brand.MAZDA),
    MX5(Brand.MAZDA),

    // Subaru Models
    IMPREZA(Brand.SUBARU),
    LEGACY(Brand.SUBARU),
    OUTBACK(Brand.SUBARU),
    FORESTER(Brand.SUBARU),
    WRX(Brand.SUBARU),

    // Volvo Models
    XC40(Brand.VOLVO),
    XC60(Brand.VOLVO),
    XC90(Brand.VOLVO),
    S60(Brand.VOLVO),
    S90(Brand.VOLVO),

    // Land Rover Models
    RANGE_ROVER(Brand.LAND_ROVER),
    DISCOVERY(Brand.LAND_ROVER),
    DEFENDER(Brand.LAND_ROVER),
    EVOQUE(Brand.LAND_ROVER),

    // Jaguar Models
    XF(Brand.JAGUAR),
    XE(Brand.JAGUAR),
    F_TYPE(Brand.JAGUAR),
    F_PACE(Brand.JAGUAR),
    I_PACE(Brand.JAGUAR),

    // Porsche Models
    PORSHCE_911(Brand.PORSCHE),
    CAYENNE(Brand.PORSCHE),
    MACAN(Brand.PORSCHE),
    PANAMERA(Brand.PORSCHE),
    TAYCAN(Brand.PORSCHE),

    // Lexus Models
    IS(Brand.LEXUS),
    ES(Brand.LEXUS),
    RX(Brand.LEXUS),
    NX(Brand.LEXUS),
    LS(Brand.LEXUS),

    // Ferrari Models
    F8_TRIBUTO(Brand.FERRARI),
    FERRARI_812_SUPERFAST(Brand.FERRARI),
    PORTOFINO(Brand.FERRARI),
    SF90_STRADALE(Brand.FERRARI),

    // Lamborghini Models
    AVENTADOR(Brand.LAMBORGHINI),
    HURACAN(Brand.LAMBORGHINI),
    URUS(Brand.LAMBORGHINI),

    // Aston Martin Models
    DB11(Brand.ASTON_MARTIN),
    VANTAGE(Brand.ASTON_MARTIN),
    DBS_SUPERLEGGERA(Brand.ASTON_MARTIN),

    // Bentley Models
    CONTINENTAL_GT(Brand.BENTLEY),
    BENTAYGA(Brand.BENTLEY),
    FLYING_SPUR(Brand.BENTLEY),

    // Rolls-Royce Models
    GHOST(Brand.ROLLS_ROYCE),
    PHANTOM(Brand.ROLLS_ROYCE),
    CULLINAN(Brand.ROLLS_ROYCE),

    // Maserati Models
    GHIBLI(Brand.MASERATI),
    QUATTROPORTE(Brand.MASERATI),
    LEVANTE(Brand.MASERATI),
    // Bugatti Models
    CHIRON(Brand.BUGATTI),
    VEYRON(Brand.BUGATTI),

    // Acura Models
    TLX(Brand.ACURA),
    RDX(Brand.ACURA),
    MDX(Brand.ACURA),

    // Lincoln Models
    NAVIGATOR(Brand.LINCOLN),
    AVIATOR(Brand.LINCOLN),
    CORSAIR(Brand.LINCOLN),

    // Cadillac Models
    ESCALADE(Brand.CADILLAC),
    XT5(Brand.CADILLAC),
    CT5(Brand.CADILLAC),

    // GMC Models
    SIERRA(Brand.GMC),
    ACADIA(Brand.GMC),
    YUKON(Brand.GMC),

    // Dodge Models
    CHARGER(Brand.DODGE),
    CHALLENGER(Brand.DODGE),
    DURANGO(Brand.DODGE),

    // Jeep Models
    WRANGLER(Brand.JEEP),
    CHEROKEE(Brand.JEEP),
    GRAND_CHEROKEE(Brand.JEEP),

    // Ram Models
    RAM_1500(Brand.RAM),
    RAM_2500(Brand.RAM),

    // Chrysler Models
    PACIFICA(Brand.CHRYSLER),
    CHRYSLER_300(Brand.CHRYSLER),

    // Buick Models
    ENCLAVE(Brand.BUICK),
    ENCORE(Brand.BUICK),
    ENVISION(Brand.BUICK),

    // MINI Models
    COOPER(Brand.MINI),
    COUNTRYMAN(Brand.MINI),

    // Saab Models
    SAAB_9_3(Brand.SAAB),
    SAAB_9_5(Brand.SAAB),

    // Skoda Models
    OCTAVIA(Brand.SKODA),
    SUPERB(Brand.SKODA),
    KODIAQ(Brand.SKODA),

    // SEAT Models
    IBIZA(Brand.SEAT),
    LEON(Brand.SEAT),
    ATECA(Brand.SEAT),

    // Tata Models
    NEXON(Brand.TATA),
    HARRIER(Brand.TATA),
    SAFARI(Brand.TATA),
    // Mahindra Models
    SCORPIO(Brand.MAHINDRA),
    XUV500(Brand.MAHINDRA),
    THAR(Brand.MAHINDRA),

    // SsangYong Models
    TIVOLI(Brand.SSANGYONG),
    REXTON(Brand.SSANGYONG),
    KORANDO(Brand.SSANGYONG);

    private final Brand brand;

    Model(Brand brand) {
        this.brand = brand;
    }

    public Brand getBrand() {
        return this.brand;
    }
}