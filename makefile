SRC_DIR := src
BIN_DIR := bin
COMPILER := javac
JVM := java
COMPILE_FLAGS := -d $(BIN_DIR) -sourcepath $(SRC_DIR)

SRC := 	$(wildcard $(SRC_DIR)/*.java) \
		$(wildcard $(SRC_DIR)/Model/*.java) \
		$(wildcard $(SRC_DIR)/View/*.java) \
		$(wildcard $(SRC_DIR)/Controller/*.java) \
		$(wildcard $(SRC_DIR)/Utils/*.java) \
		$(wildcard $(SRC_DIR)/FileManagement/*.java)

BIN := $(patsubst $(SRC_DIR)/%.java,$(BIN_DIR)/%.class,$(SRC))

.PHONY: all run clean

all: $(BIN)

$(BIN_DIR)/%.class: $(SRC_DIR)/*.java
	$(COMPILER) $(COMPILE_FLAGS) $<

run:
	$(JVM) -cp $(BIN_DIR) Bilancio

clean:
	find $(BIN_DIR) -name "*.class" -delete

