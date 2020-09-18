export GO111MODULE=on
export PATH="$PATH:$(go env GOPATH)/bin"

protoc \
    --go_out=Mgrpc/service_config/service_config.proto=/internal/proto/grpc_service_config:transfers \
    --go-grpc_out=Mgrpc/service_config/service_config.proto=/internal/proto/grpc_service_config:transfers \
    --go_opt=paths=source_relative \
    --go-grpc_opt=paths=source_relative \
    --proto_path=../src/main/protobuf/ \
    ../src/main/protobuf/transfer.proto
