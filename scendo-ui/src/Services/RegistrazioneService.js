import axios from "axios";

const REGISTRAZIONE_API_BASE_URL = "http://localhost:8080/api/registrazione";

class RegistrazioneService{

    salvaUtente(utente){
        return axios.post(REGISTRAZIONE_API_BASE_URL, utente);
    }
}

export default new RegistrazioneService();