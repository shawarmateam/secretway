public class MyJavaClass {
    static {
        String lib_path = "/home/adisteyf/apps/java/secretway/libs-cpp";
        System.load(lib_path+"/testjni.so");
    }

    public native String swDecryptMsg(String msg, String pr_key);

    public static void main(String[] args) {
        String msg = "U1dT77+91r/vv71J3oNJJu+/ve+/ve+/vRgFN0Pvv73vv70fR++/vVfvv73vv73vv70eVe+/vRZg77+977+977+9Je+/ve+/vRIIxZfvv73vv70FZwMy77+977+9WQjvv73fkMuGWhs8GEzvv73co1YZLxnvv715TkLvv71S77+9Q++/vdeDVU3vv73vv73vv73vv71JQu+/ve+/ve+/vcuHP++/vfOws73vv73vv71+fE9/Ks6cw5Lvv73vv70k77+9aDDvv73vv71G77+9aC8TKBJCbStvdxHvv717CVM3xpwh77+9dm3vv71n77+977+9Sg03UHRx77+9GO+/ve+/vWfvv71h3Y3vv70p77+9W++/ve+/ve+/ve+/vTo9Cu+/vQ41Ku+/vUZNTS3vv73vv73Zpu+/ve+/vUzvv73vv70Q77+977+9a++/ve+/vRbvv71677+9P++/ve+/vUPvv70m77+9ae+/vSZi77+9DBp677+9HHHvv73Xnu+/ve+/vQvvv73vv71tLe+/vQ5IelsgOe+/ve+/vSV7Klc5aSnvv7111L8TSu+/vSHvv71L77+9YzpBbkpTSDJIUDRtUGFmdkhkeG1HMmlmNmtWNTdAOEZ6OEVVbU1EdTY3Tml0dEtNaVR6b2hPQlZ5V0grdGJqYVFuR1BMS1YuZCVoRS5EUWhYYmk0cGpselJFeSFmT3dJTjMyNFpFM09kd1Nkel5XaVc0ellVUkVfMTIA";
        String pr_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuWGHxKa+FZ1MUJ7PloF8ufI15Fj+xUDTWGsrPfbP9LuNise9dO261R0A80FPw/U+iDCJZkuhEsnXCjljJkeu+yMAzmvDMESDAS1EsT30/nwFJrj6cca8SDIHSUKpKaQpFf0rt+oxVSCqxgF7Ue7r5V5WPP0l2rUro9dzY9Js7QDgp52TYjLbsstGsgEhpFg60dbTmsllEn0tu2rJs1cMLo1UyqbNUJtjobkNZq9oaUi0EdXV29y9+7xLGS0Jmn3qfZQ3C3wpvw/wmVAF8E1wFDffubZzkZI+mpQOWX3ropyUNjGkRHmcU9sfi4lIBT/FwsGG7oG3WYf3jH2us+vOzAgMBAAECggEAD3BVMAF0gRg+w6SmahmzBA9Uidn+Buom26Tgu9ISW8PIsAc4vqVSLuxh1GDQZiFQaXWwnwUC236ALhvj4emxmiH13zwDwdZsc4rX49ggK7+dYhRzh/ALREpmPqUVB2TY24GjtYI/5hdvbGuFOkTAsG7gmCBPFZrP7OREy9ORYYQvWsoDaQ6suDSgIdQOObXqsdQMPGRxLKJv1gByxs/bbXYBiC6yzS2uhNFJFHIvF7mLTcMvfljw0+R7dW1wNDjWM5Plw6zOR70RRIyRpNksFhmV18NViz5dRPHhgSA+YBQjsWcz3iHFujY+Vm8/7diENDtIfoP0ZS51GG2YJODxjQKBgQDfqgo+kD/+SLQEnQIPRck8cCmDkvE2dPBaj2zlZxgW+yts40KbTgGxd50IlYwu7lMD6IAkzoKO3zhm7ocD7GaRK+yGG1MB4399trWdz64kEsqtelGsTYbo64gyk2aTJ3OYVn/D5UCL0RPpC8clvEvqwnNRMgd88lsHWBxs2CoObQKBgQDHjQPN5vyDwaUbmk2WjczyZBz9oaAHCjEuTOw2LByiGmsdkPbJguSFtOPCxeuuh0HKGhsmupDwbbjF6qaYb3UVR4gWCueWoOV0rqk1zSJTnTsRDKNkYMoWXO2kQqh338q6rVl5pAquB5wA4ubt2Q5B+jLjEU7hzHTJuyD3RHU2nwKBgQDWiBXtbpVDBV9OMX9BQPHFu9vF7VLapg3I/1s3Ksyrdl1vwwgnSQpMu+tuyWq9tTEBRahsJXyLJ8bEzDPK6pxy0Iu9EkZrQFgQMAlCBmsFBNVM0k+2kmI91XU89a9kpGm3C/je4G0p7vBHD1oEcvjqg3ryvab7DMeNYtFZAXpNfQKBgQCLCx4IfZebsvV5y0WezwVsLAfrTpAz940XRvAcOxZkeWYDvvvGo5bZ95XwJHq5LHPR/+jFTqApdzqVZZBZjOgXQLV5LnX6Bfh8Giu7Wkk928PJeHXtPmfxtyS7HNtIU75t3tYGpR/v7Uttbq0cM+70WALXJztAqHcEDYjF4oSN3wKBgDs6jfoPNovQH2iwqdzftt4RcfntZjJzmqHAo4k6+mNjcjywhtE9czyluVfMEPhfyZCJh9BqFTLU+1bw6YyT2cGaFJS8rSY2L6nah6f5KLKys77/ZXKcSzSnnlgQUoEPpM/Q6IoXK+KMEx7vLR3QXLLSsZM7ZxJfRGzI9D2ITAdy";

        new MyJavaClass().swDecryptMsg(msg, pr_key);
    }
}

