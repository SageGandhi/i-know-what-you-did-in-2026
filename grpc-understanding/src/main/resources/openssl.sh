# Step 1: Generate Certificate Authority + Trust Certificate (certificate.authority.pem)
openssl genrsa -passout pass:s7cr37! -aes256 -out certificate.authority.key 4096
openssl req -passin pass:s7cr37! -new -x509 -days 365 -key certificate.authority.key -out certificate.authority.pem -subj "/CN=certificate.authority"

# Step 2: Generate the Server Private Key (server.key)
openssl genrsa -passout pass:s7cr37! -aes256 -out server.key 4096
# Step 3: Get a certificate signing request from the CA (server.csr)
openssl req -passin pass:s7cr37! -new -key server.key -out server.csr -subj "/CN=localhost"

# Step 4: Sign the certificate with the CA we created (it's called self signing) - server.pem
openssl x509 -req -passin pass:s7cr37! -days 365 -in server.csr -CA certificate.authority.pem -CAkey certificate.authority.key -set_serial 01 -out server.pem
# Step 5: Convert the server certificate to .pem format (server.pem) - usable by gRPC
openssl pkcs8 -topk8 -nocrypt -passin pass:s7cr37! -in server.key -out server.key.pem