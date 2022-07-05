import axios from "axios";
const API_URL = "http://localhost:8080/api/";
class AuthService {
  login(utente) {
    return axios
      .post(API_URL + "login", utente)
      .then(response => {
        if (response.data.message) {
          localStorage.setItem("user", JSON.stringify(response.data.message));
          console.log("Ho salvato user ");
        }
        return response.data;
      });
  }
  logout() {
    localStorage.removeItem("user");
  }
  register(utente) {
    return axios.post(API_URL + "registrazione", utente);
  }
  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}
export default new AuthService();