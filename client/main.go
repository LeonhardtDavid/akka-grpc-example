package main

import (
	"context"
	"davitu.com/transfers/transfers"
	"fmt"
	"google.golang.org/grpc"
	"log"
)

func main() {
	conn, err := grpc.Dial("127.0.0.1:8080", grpc.WithInsecure())
	if err != nil {
		log.Fatalln("Connection error", err)
	}
	defer conn.Close()

	client := transfers.NewTransferServiceClient(conn)

	response, err := client.Execute(
		context.Background(),
		&transfers.TransferRequest{
			From:   "uno",
			To:     "dos",
			Amount: 1000,
		},
	)
	if err != nil {
		log.Fatalln("Response Error", err)
	}

	log.Println(fmt.Sprintf("Request: {%s}, Status: %s, Info: %s", response.Request, response.Status, response.Info))
}
