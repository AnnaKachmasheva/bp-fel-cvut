import React from "react"
import Button, {ButtonSize, ButtonType} from "../../components/button/Button";
import {useNavigate} from "react-router-dom";
import {removeToken} from "../../services/auth";
import {BsQrCodeScan} from "react-icons/bs";
import {FaListCheck} from "react-icons/fa6";
import {IoExitOutline} from "react-icons/io5";
import styles from './Home.module.scss';


function HelloPage() {

    let navigate = useNavigate();

    const handleGoScan = () => {
        navigate('/app/scan');
    };

    const handleGoShowOrder = () => {
        navigate('/app/orders');
    };

    const handleLogout = () => {
        removeToken()
        window.location.reload();
    };


    return (
        <div className={'main-content '.concat(styles.container)}>
            <div className={'hello-content'}>

                <Button type={ButtonType[0].type}
                        onClick={handleGoScan}
                        size={ButtonSize[1].size}
                        label={'Scan'}
                        icon={<BsQrCodeScan/>}
                        isIconEnd={true}/>

                <Button type={ButtonType[2].type}
                        onClick={handleGoShowOrder}
                        size={ButtonSize[1].size}
                        label={'Show orders'}
                        icon={<FaListCheck/>}
                        isIconEnd={true}/>

                <Button type={ButtonType[3].type}
                        onClick={handleLogout}
                        size={ButtonSize[1].size}
                        label={'Log out'}
                        icon={<IoExitOutline/>}
                        isIconEnd={true}/>

            </div>
        </div>
    )
}

export default HelloPage;