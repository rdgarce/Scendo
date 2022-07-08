import React from 'react'
import {Stack} from 'react-bootstrap'
import AuthService from '../Services/AuthService';
import UscitaService from '../Services/UscitaService';
import { useNavigate } from "react-router-dom";

const ContainerInviti = ({invito}) => {

    const navigate = useNavigate()

    const accettaInvito = (e, idUscita) => {
        e.preventDefault();
        UscitaService.accettaInvito(idUscita).then((response) => {
            console.log(response);
            navigate(0);
        }).catch((error) => {
            console.log(error);
            if(error.response.status === 401){
                AuthService.logout();
                navigate("/login");
                navigate(0);
            }
        });
    }

    const rifiutaInvito = (e, idUscita) => {
        e.preventDefault();
        UscitaService.rifiutaInvito(idUscita).then((response) => {
            console.log(response);
            navigate(0);
        }).catch((error) => {
            console.log(error);
            if(error.response.status === 401){
                AuthService.logout();
                navigate("/login");
                navigate(0);
            }

        });
    }

  return (
    <div className='container mx-auto my-8 border-1' 
    key={invito.message.idUscita}>
        <h5>
            {invito.message.emailInvitante + " ti ha invitato"}
        </h5>
        <div>
            {"Tipo Uscita: \t" + invito.message.tipoUscita}
        </div>
        <div>
            {"Data: \t" + invito.message.dataEOra.replace("T", " ").substring(0, 16)}
        </div>
        <div>
            {"Location incontro: \t" + invito.message.locationIncontro}
        </div>
        <div>
            {"Location uscita: \t" + invito.message.locationUscita}
        </div>
        <div>
            {"Descrizione: \t" + invito.message.descrizione}
        </div>
        <Stack direction='horizontal' gap={3}>
        <button
            onClick={e => accettaInvito(e, invito.message.idUscita)}
            type="submit"
            className="w-full text-center px-1 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1"
            >Accetta
        </button>
        <button
            onClick={e => rifiutaInvito(e, invito.message.idUscita)}
            type="submit"
            className="w-full text-center px-1 rounded bg-red-400 text-white hover:bg-red-700 focus:outline-none my-1"
            >Rifiuta
        </button>
        </Stack>
    </div>
  )
}

export default ContainerInviti