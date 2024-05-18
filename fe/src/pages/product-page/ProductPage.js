import React, {useEffect, useState} from "react";
import {showStatus, toStringForQRCode} from "../../utils/Common";
import styles from './ProductPage.module.scss';
import {useLocation, useNavigate, useParams} from "react-router-dom";
import Button, {ButtonSize, ButtonType} from "../../components/button/Button";
import QRCode from "qrcode.react";
import stylesStorage from "../storage-page/StoragePage.module.scss";
import {MdPassword} from "react-icons/md";
import {BsPencil} from "react-icons/bs";
import {IoMdClose} from "react-icons/io";
import {ModalDeleteProductConfirm} from "../storage-page/modal-delete-product/ModalDeleteProductConfirm";
import {ModalUpdateProduct} from "../storage-page/modal-update-product/ModalUpdateProduct";
import {userApi} from "../../services/api";
import {removeToken} from "../../services/auth";


function ProductPage(props) {

    const navigate = useNavigate();

    const [showPrintCode, setShowPrintCode] = useState(false);
    const [showConfirmDeleteProduct, setShowConfirmDeleteProduct] = useState(false);
    const [showUpdateProduct, setShowUpdateProduct] = useState(false);
    const [qrCodeSize, setQRCodeSize] = useState(100);
    const [categories, setCategories] = useState([]);
    const [statuses, setStatuses] = useState([]);

    const {idProduct} = useParams();
    const {state} = useLocation();

    const product = state.product;
    const status = state.product.status?.name == null ? "" : state.product.status.name.toLowerCase();

    useEffect(() => {
        fetchCategories().then(() => {
            fetchStatuses().then();
        });
    }, []);

    const fetchCategories = async () => {
        try {
            const categoriesData = await userApi.getAllCategories();
            setCategories(categoriesData.data);
        } catch (error) {
            removeToken();
            navigate('/app/logout');
        }
    };

    const fetchStatuses = async () => {
        try {
            const statuses = await userApi.getAllStatuses();
            setStatuses(statuses.data);
        } catch (error) {
            removeToken();
            navigate('/app/logout');
        }
    };

    const printQRCode = () => {
        const qrCodeCanvas = document.querySelector('canvas');
        const qrCodeImage = qrCodeCanvas.toDataURL("image/png");
        const printWindow = window.open('', '_blank');
        printWindow.document.write('<img src="' + qrCodeImage + '" onload="window.print();window.close()" />');
        printWindow.document.close();
    }

    const handleSizeChange = (event) => {
        setQRCodeSize(Number(event.target.value));
    };


    return (
        <div className={'content'}>
            <ModalDeleteProductConfirm onClose={() => setShowConfirmDeleteProduct(false)}
                                       show={showConfirmDeleteProduct}
                                       product={product}/>

            <ModalUpdateProduct onClose={() => setShowUpdateProduct(false)}
                                show={showUpdateProduct}
                                product={product}
                                categories={categories}
                                statuses={statuses}/>

            <div className={styles.wrapper}>
                <div className={styles.container}>
                    <img className={styles.image}
                         src={'todo'}
                         alt={'image'}/>


                    <div className={styles.mainInfo}>
                    <span className={styles.category}>
                        [{product.category?.name}]
                    </span>

                        <h2>{product.name}</h2>

                        <span className={status.concat(" ").concat(stylesStorage.status)}>
                         {showStatus(status)}
                    </span>

                        <p className={styles.description}>{product.description}</p>
                        <br/>

                        <p className={styles.date}>created: {product.createdAt}</p>
                        <p className={styles.date}>updated: {product.updatedAt}</p>

                        <div className={'buttons'}>
                            <Button label={'Edit'}
                                    type={ButtonType[2].type}
                                    size={ButtonSize[0].size}
                                    onClick={() => setShowUpdateProduct(true)}
                                    icon={<MdPassword/>}/>


                            <Button label={'Delete'}
                                    type={ButtonType[4].type}
                                    size={ButtonSize[0].size}
                                    onClick={() => setShowConfirmDeleteProduct(true)}
                                    icon={<BsPencil/>}/>

                        </div>
                    </div>
                </div>
            </div>

            <div className={styles.codeContainer.concat(" ").concat(showPrintCode ? styles.openShowCode : "")}>
                {showPrintCode ?
                    <IoMdClose size={30}
                               className={styles.icon}
                               onClick={() => setShowPrintCode(false)}/> : null}
                {showPrintCode ?
                    <div className={styles.codeInfo}>

                        <h6 className={styles.titleSelectSize}>Select size QR code</h6>

                        <div className={styles.sizeQRCodeOptions}>
                            <label className={(qrCodeSize === 100) ? styles.selectedSize : ""}>
                                <input type="radio"
                                       value="100"
                                       checked={qrCodeSize === 100}
                                       onChange={handleSizeChange}/>
                                Small
                            </label>
                            <label className={(qrCodeSize === 200) ? styles.selectedSize : ""}>
                                <input type="radio"
                                       value="200"
                                       checked={qrCodeSize === 200}
                                       onChange={handleSizeChange}/>
                                Medium
                            </label>
                            <label className={(qrCodeSize === 300) ? styles.selectedSize : ""}>
                                <input type="radio"
                                       value="300"
                                       checked={qrCodeSize === 300}
                                       onChange={handleSizeChange}/>
                                Large
                            </label>
                        </div>

                        <QRCode value={toStringForQRCode(product.id)}
                                size={qrCodeSize}
                                className={styles.code}
                        />
                    </div> : null}

                <div className={styles.buttonPrintCode}>
                    <Button label="Print QR Code"
                            type={ButtonType[3].type}
                            size={ButtonSize[0].size}
                            onClick={!showPrintCode ? () => setShowPrintCode(true) : printQRCode}/>
                </div>

            </div>
        </div>
    )
}

export default ProductPage;