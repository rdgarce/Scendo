import React, { useState } from 'react'
import AuthService from '../Services/AuthService';
import { useNavigate } from "react-router-dom";

const Login = () => {

    const navigate = useNavigate()
    
    const [utente, setUtente] = useState({
        username: "",
        password: "",
    });

    const [errore, setErrore] = useState({
        messaggio: "",
    });
    
    const handleChange = (e) => {
        const value = e.target.value;
        setUtente({...utente, [e.target.name]: value});
    };

    const loginUtente = (e) => {
        e.preventDefault();
        AuthService.login(utente).then((response) => {
            setErrore({...errore, messaggio: ""});
            navigate("/home");
            navigate(0);
            console.log(response);
        }).catch((error) => {
            console.log(error);
            if(error.response.data.message)
                setErrore({...errore, messaggio: error.response.data.message});
            else
                setErrore({...errore, messaggio: "Errore"});
        });
    }
    
    return (
    <div className="min-h-screen flex flex-col">
            <div className="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
                <div className="bg-white px-6 py-8 rounded shadow-md text-black w-full">
                    <h1 className="mb-8 text-3xl text-center">Login</h1>
                    <input 
                        type="text"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="username"
                        value={utente.username}
                        placeholder="Email" />

                    <input 
                        type="password"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="password"
                        value={utente.password}
                        placeholder="Password" />
                    <button
                        onClick={loginUtente}
                        type="submit"
                        className="w-full text-center py-3 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1"
                    >Login</button>
                    
                    <div
                        type="text"
                        className="text-red-500"
                    >{errore.messaggio} </div>
                </div>

                <div className="text-grey-dark mt-6">
                    Non hai un account? 
                    <a className="no-underline border-b border-blue-600 text-blue-600" href="../registrazione/">
                        Registrati
                    </a>.
                </div>
            </div>
    </div>
  )
}

export default Login;