#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/time.h>
#include <fcntl.h>
#include <math.h>
#include <popt.h>
#include <libgen.h>

#include "sss.h"

void print_data(uint8_t *data)
{
	printf("data: ");
	int i;
	for (i = 0; i < sss_MLEN; i++) {
		printf("%02x", data[i]);
	}
	printf("\n");
}

int main(int argc, char *argv[])
{
	int n = 3;
	int k = 2;
	sss_Share share[n];
	uint8_t data[sss_MLEN];

	memset(data, 0, sss_MLEN);
	int i;
	for (i = 0; i < sss_MLEN; i++) {
		data[i] = i;
	}
	print_data(data);

	printf("creating shares...\n");

	sss_create_shares(share, data, n, k);

	int ns, nb;
	for (ns = 0; ns < n; ns++) {
		printf("share %d:\n", ns);
		sss_Share * s = &share[ns];
		for (nb = 0; nb < sss_SHARE_LEN; nb++) {
			uint8_t b = (*s)[nb];
			printf("%x", b);
		}
		printf("\n");
	}

	printf("recovering data...\n");

	uint8_t recovered[sss_MLEN];

	sss_combine_shares(recovered, share, k);
	print_data(recovered);

	return 0;
}
