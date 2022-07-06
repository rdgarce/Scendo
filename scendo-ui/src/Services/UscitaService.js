import axios from 'axios';
import authHeader from './AuthHeader';

const API_URL = "http://localhost:8080/api/";

class UscitaService{

    creaUscita(uscita){
        return axios
            .post(API_URL + "crea-uscita", uscita, { headers: authHeader()})
            .then(response => {
                return response.data;
            });
    }

}
export default new UscitaService();