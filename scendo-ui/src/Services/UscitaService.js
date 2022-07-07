import axios from 'axios';
import authHeader from './AuthHeader';

const API_URL = "http://localhost:8080/api/";

class UscitaService{

    async creaUscita(uscita){
        const response = await axios
            .post(API_URL + "crea-uscita", uscita, { headers: authHeader() });
        return response.data;
    }

    async calendarioUscite(){
        const response = await axios.get(API_URL + "calendario-uscite", { headers: authHeader() });
        return response.data;
    }

    async infoUscita(id, part){
        const response = await axios.get(API_URL + "uscita/" + id, { headers: authHeader(), params: { partecipanti: part } });
        return response.data;
    }

}
export default new UscitaService();