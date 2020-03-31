BIN = test

SRC = \
src/main.c

OBJ = \
src/main.o

CC  = /usr/bin/gcc
DEPENDFILE = .depend
CFLAGS  = -g -Wall -I/tmp/sss/sss/
LDFLAGS = -lsss -L/tmp/sss/sss/

test: $(OBJ)
	$(CC) $(CFLAGS) -o $(BIN) $(OBJ) $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

dep: $(SRC)
	$(CC) $(CFLAGS) -MM $(SRC) > $(DEPENDFILE)

clean:
	rm -f $(BIN) $(OBJ)

halfclean:
	rm -f $(OBJ)

-include $(DEPENDFILE)
