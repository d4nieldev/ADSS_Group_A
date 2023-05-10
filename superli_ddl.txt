---------------- Discounts ----------------------

CREATE TABLE IF NOT EXISTS Discounts (
	id        INTEGER NOT NULL,
	startDate TEXT    NOT NULL,
	endDate   TEXT    NOT NULL,
	val       REAL    NOT NULL,
	dType     TEXT    NOT NULL,
	
	CHECK(dType IN ('Fixed', 'Precentage')),
	
	PRIMARY KEY (id)
);

---------------- Suppliers ----------------------

CREATE TABLE IF NOT EXISTS Suppliers (
	id               INTEGER NOT NULL,
	name             TEXT    NOT NULL,
	bankAccount      TEXT    NOT NULL,
	paymentCondition TEXT    NOT NULL,
	
	CHECK(paymentCondition IN ('net 30 EOM', 'net 60 EOM')),
	
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Contacts (
	supplierId INTEGER NOT NULL,
	phone      TEXT    NOT NULL,
	name       TEXT    NOT NULL,
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	PRIMARY KEY (supplierId, phone)
);

CREATE TABLE IF NOT EXISTS SuppliersFields (
	supplierId INTEGER NOT NULL,
	fieldName  INTEGER NOT NULL,
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	FOREIGN KEY (fieldName)  REFERENCES Fields(name)  ON DELETE CASCADE,
	PRIMARY KEY (supplierId, fieldName)
);

CREATE TABLE IF NOT EXISTS SupplierAmountToDiscount (
	supplierId INTEGER NOT NULL,
	amount     INTEGER NOT NULL,
	discount   INTEGER NOT NULL UNIQUE,
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	FOREIGN KEY (discount)   REFERENCES Discounts(id) ON DELETE CASCADE,
	PRIMARY KEY (supplierId, amount)
);

CREATE TABLE IF NOT EXISTS FixedDaysSuppliers (
	supplierId  INTEGER NOT NULL,
	dayOfSupply INTEGER NOT NULL,
	
	CHECK(dayOfSupply BETWEEN 1 AND 7),
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	PRIMARY KEY (supplierId, dayOfSupply)
);

CREATE TABLE IF NOT EXISTS OnOrderSuppliers (
	supplierId    INTEGER NOT NULL,
	maxSupplyDays INTEGER NOT NULL,
	
	CHECK (maxSupplyDays >= 0),
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	PRIMARY KEY (supplierId)
);

CREATE TABLE IF NOT EXISTS SelfPickupSuppliers (
	supplierId         INTEGER NOT NULL,
	maxPreperationDays INTEGER NOT NULL,
	address            TEXT    NOT NULL,
	
	CHECK (maxPreperationDays >= 0),
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	PRIMARY KEY (supplierId)
); 

------------------ Branches ------------------------

CREATE TABLE IF NOT EXISTS Branches (
	id   INTEGER NOT NULL,
	name TEXT    NOT NULL,
	
	PRIMARY KEY (id)
);

------------------ Products ------------------------

CREATE TABLE IF NOT EXISTS Categories (
	id     INTEGER NOT NULL,
	name   TEXT    NOT NULL,
	parent INTEGER,
	
	FOREIGN KEY (parent) REFERENCES Categories(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Products (
	id           INTEGER NOT NULL,
	name         TEXT    NOT NULL,
	manufacturer TEXT    NOT NULL,
	categoryId   INTEGER NOT NULL,
	
	FOREIGN KEY (categoryId) REFERENCES Categories(id) ON DELETE SET NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ProductBranch (
	productId   INTEGER NOT NULL,
	branchId    INTEGER NOT NULL,
	price       REAL    NOT NULL,
	minQuantity INTEGER NOT NULL,
	
	CHECK (minQuantity > 0 AND price >= 0),
	
	FOREIGN KEY (productId) REFERENCES Product(id) ON DELETE CASCADE,
	FOREIGN KEY (branchId)  REFERENCES Branch(id)  ON DELETE CASCADE,
	PRIMARY KEY(branchId, productId)
);

CREATE TABLE IF NOT EXISTS ProductBranchDiscounts (
	productId  INTEGER NOT NULL,
	branchId   INTEGER NOT NULL,
	discountId INTEGER NOT NULL,
	
	FOREIGN KEY (productId, branchId) REFERENCES ProductBranch(productId, branchId) ON DELETE CASCADE,
	FOREIGN KEY (discountId)          REFERENCES Discounts(id)                      ON DELETE SET NULL,
	PRIMARY KEY (discountId)
);

CREATE TABLE IF NOT EXISTS SpecificProduct (
	specificId INTEGER NOT NULL,
	generalId  INTEGER NOT NULL,
	branchId   INTEGER NOT NULL,
	buyPrice   REAL    NOT NULL,
	sellPrice  REAL,
	status     TEXT,
	flaw       TEXT,
	expDate    TEXT    NOT NULL,
	
	CHECK (buyPrice >= 0 AND sellPrice >= 0 AND status IN ('SOLD', 'ON_SHELF', 'ON_STORAGE')),
	
	FOREIGN KEY (generalId, branchId) REFERENCES ProductBranch(productId, branchId) ON DELETE SET NULL,
	PRIMARY KEY (specificId)
);

---------------- Agreements ----------------------

CREATE TABLE IF NOT EXISTS ProductAgreement (
	supplierId        INTEGER NOT NULL,
	productId         INTEGER NOT NULL,
	stockAmount       INTEGER NOT NULL,
	basePrice         REAL NOT NULL,
	productSupplierId INTEGER NOT NULL, -- the catalogical number of the product in the supplier system
	
	CHECK(stockAmount >= 0 AND basePrice >= 0),
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	FOREIGN KEY (productId)  REFERENCES Products(id)  ON DELETE CASCADE,
	PRIMARY KEY (supplierId, productId)
);

CREATE TABLE IF NOT EXISTS AgreementAmountToDiscount (
	supplierId INTEGER NOT NULL,
	productId  INTEGER NOT NULL,
	amount     INTEGER NOT NULL,
	discount   INTEGER NOT NULL,
	
	CHECK (amount > 0),
	
	FOREIGN KEY (supplierId, productId) REFERENCES ProductAgreement(supplierId, productId) ON DELETE CASCADE,
	FOREIGN KEY (discount)              REFERENCES Discounts(id),
	PRIMARY KEY (discount)
	
);

---------------- Reservations ----------------------

CREATE TABLE IF NOT EXISTS Reservations (
	id                INTEGER NOT NULL,
	supplierId        INTEGER,
	rDate             TEXT    NOT NULL,
	status            TEXT    NOT NULL,
	destinationBranch INTEGER,
	
	CHECK(status IN ('NotReady', 'Ready', 'Closed', 'Aborted')),
	
	FOREIGN KEY (supplierId)        REFERENCES Suppliers(id) ON DELETE SET NULL,
	FOREIGN KEY (destinationBranch) REFERENCES Branch(id)    ON DELETE SET NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ReceiptItem (
	reservationId              INTEGER NOT NULL,
	productId                  INTEGER,
	amount                     INTEGER NOT NULL,
	pricePerUnitBeforeDiscount REAL    NOT NULL,
	pricePerUnitAfterDiscount  REAL    NOT NULL,
	
	CHECK(amount > 0),
	
	FOREIGN KEY (reservationId) REFERENCES Reservations(id) ON DELETE CASCADE,
	FOREIGN KEY (productId)     REFERENCES Products(id)     ON DELETE SET NULL,
	PRIMARY KEY (reservationId, productId)
);

CREATE TABLE IF NOT EXISTS PeriodicReservation (
	supplierId INTEGER NOT NULL,
	branchId   INTEGER NOT NULL,
	dayOfOrder INTEGER NOT NULL,
	
	CHECK(dayOfOrder BETWEEN 1 AND 7),
	
	FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
	FOREIGN KEY (branchId)   REFERENCES Branch(id)    ON DELETE CASCADE,
	PRIMARY KEY (supplierId, branchId)
);

CREATE TABLE IF NOT EXISTS PeriodicReservationItem (
	supplierId INTEGER NOT NULL,
	branchId   INTEGER NOT NULL,
	productId  INTEGER NOT NULL,
	amount     INTEGER NOT NULL,
	
	CHECK (amount > 0),
	
	FOREIGN KEY (supplierId, branchId) REFERENCES PeriodicReservation(supplierId, branchId) ON DELETE CASCADE,
	FOREIGN KEY (productId, branchId)  REFERENCES ProductBranch(productId, branchId)        ON DELETE CASCADE,
	PRIMARY KEY (supplierId, branchId, productId)
);

---------------- Reports ----------------------

CREATE TABLE IF NOT EXISTS Reports (
	id          INTEGER NOT NULL,
	branchId    INTEGER,
	createdDate TEXT NOT NULL,
	
	FOREIGN KEY (branchId) REFERENCES Branch(id),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ReportEntries (
	id       INTEGER NOT NULL,
	reportId INTEGER NOT NULL,
	
	FOREIGN KEY (reportId) REFERENCES Reports(reportId) ON DELETE CASCADE,
	PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS InventoryReportEntries (
	entryId       INTEGER NOT NULL,
	productId     INTEGER NOT NULL,
	shelfAmount   INTEGER NOT NULL,
	storageAmount INTEGER NOT NULL,
	
	CHECK (shelfAmount >= 0 AND storageAmount >= 0),
	
	FOREIGN KEY (entryId)   REFERENCES ReportEntries(id) ON DELETE CASCADE,
	FOREIGN KEY (productId) REFERENCES Products(id)      ON DELETE SET NULL,
	PRIMARY KEY (entryId)
);

CREATE TABLE IF NOT EXISTS DeficiencyReportEntries (
	entryId       INTEGER NOT NULL,
	productId     INTEGER NOT NULL,
	missingAmount INTEGER NOT NULL,
	
	CHECK (missingAmount >=0),
	
	FOREIGN KEY (entryId)   REFERENCES ReportEntries(id) ON DELETE CASCADE,
	FOREIGN KEY (productId) REFERENCES Products(id)      ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS ExpiredAndFlawReportEntries (
	entryId    INTEGER NOT NULL,
	specificId INTEGER NOT NULL,
	
	FOREIGN KEY (entryId)    REFERENCES ReportEntries(id)           ON DELETE CASCADE,
	FOREIGN KEY (specificId) REFERENCES SpecificProduct(specificId) ON DELETE SET NULL
)
