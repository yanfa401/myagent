package com.xielei.workflowagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

/**
 * FC增强的字节码Transformer
 *
 * @author xielei
 * @date 2023/6/21 16:20
 */
public class FCTransformer implements ClassFileTransformer {

    /**
     * 待增强的FC列表Class名称
     */
    private static final List<String> CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST = new ArrayList<>();

    private static final List<String> ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST = new ArrayList<>();

    /**
     * CLASS_POOL
     */
    private static final ClassPool CLASS_POOL = ClassPool.getDefault();

    static {
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.notify.CalcExpIPPNotifyDate".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.notify.advice.CalcExpFellowOfferNotifyDate".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.notify.advice.AdviseAtOfferProdAttr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.notify.advice.AdviseAtOrderForOtherSubs".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.notify.advice.AdviseAtFellowNbr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.notify.AdjustBalance".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.validate.CheckEventAcm".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.archive.UpdateEventAcm".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.osn.oc.component.fc.reserveFee.AdjustBalanceForOM".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CreateBalance".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcBeforeExpIPPNotifyDate".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.NotifyDNC".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.ProlongBalExpDate".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcRecurringFeeAtOffer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcExpIPPNotifyDate".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.advice.AdviseAtOffer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.advice.AdviseAtProdState".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.advice.AdviseAtActivation".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.advice.AdviseAtMainOffer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.advice.AdviseAtFellowNbr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcRecurringFeeAtMainOffer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CancelOfferBalance".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcProdInstLifecycle".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.TransferBalance".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CancelBalance".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcRecurringFeeAtAcct".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.cdr.CreateCdr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.DealFee".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.TriggerRAR".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcRecurringFeeAtProdState".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.CalcRecurringFeeAtBillingCycleType".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.notify.UpdateOrderState2C".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckUncompletedOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckOfferRelation".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckFellowNbr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckChannel".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckSimCard".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckOfferOrderRule".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckTermination".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckMainOfferRelation".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckGroupMem".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckBundleMem".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckBlockReason".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckDuplicateOffer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckIfActiveBenefit".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckIfOweCharge".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckBcMember".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckEventAcm".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckAccNbr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckSubsEvent".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.validate.CheckSupplementaryLine".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.LockResource".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpOrderId".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpForBundleOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.addorder.AddOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.LockActiveBenefit".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpProdInstFuncOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.ConfirmSimCard".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.StoreFeeInfo".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpOfferObjInstRelOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpBalShareOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpProdCreditLimitOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.CreateCustAcct".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.UpdateOrderState2B".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpForNewConnection".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpOfferInstId".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.submit.FillUpBcMemberOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.basearchive.BaseArchive".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.BuildProdInstIndex".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.Restoration".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.DealNumberPortIn".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.DealOfferInstChange".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.CalcDateForActivation".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.ResetSubsLimitNumber".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.DealNumberShifting".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.UpdateProdState".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.UpdateEventAcm".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.Transfer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.UpdateResState".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.archive.Termination".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.intf.FillUpNecessaryOffer".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.intf.FillUpOrderForStandard".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.intf.FillUpOrderForBasic".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitPrincipalSuppleOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitAdditionalOfferOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.refresh.DealSpecialGroup".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.refresh.RefreshBmCache".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.refresh.SendMessageToSpc".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.deductfee.DeductFee".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.deductfee.DeductFeeSync".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.deductfee.UpdateCustOrderState2C".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.deductfee.ReportOfferSubscription".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.manual.ProvisioningOrder".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.supplement.FillUpForFellowNbr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.supplement.GenerateOrderItem".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.supplement.FillUpForOfferProdAttr".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.reservefee.UpdateOrderState2O".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.reservefee.ReserveFee".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.qryfee.FeeInquiry".trim());
        CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.orange.oc.component.fc.notify.cdr.CreateCdr".trim());



        ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitProdInstOrder".trim());
        ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitProdInstStateOrder".trim());
        ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitResOrder".trim());
        ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitOrderItem".trim());
        ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitProdCreditLimitOrder".trim());
        ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.component.fc.initialize.InitCustInfoOrder".trim());
    }




    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (className != null) {
                String currentClass = className.replaceAll("/", ".");

                // 只针对fc进行加强
                if (CUST_ORDER_CANDIDATE_TRANS_CLASS_LIST.contains(currentClass)) {
                    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                    CLASS_POOL.insertClassPath(new LoaderClassPath(contextClassLoader));
                    CtClass ctClass = CLASS_POOL.get(currentClass);
                    CtMethod ctMethod = ctClass.getDeclaredMethod("execute");
                    ctMethod.insertBefore("com.xielei.workflowagent.util.FCCustOrderChangeTracer.trace($0.getClass().getName(), $1.getCustOrder(), true);");
                    ctMethod.insertAfter("com.xielei.workflowagent.util.FCCustOrderChangeTracer.trace($0.getClass().getName(), $1.getCustOrder(), false);", true);
                    return ctClass.toBytecode();
                }
                else if (ORDER_ITEM_CANDIDATE_TRANS_CLASS_LIST.contains(currentClass)) {
                    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                    CLASS_POOL.insertClassPath(new LoaderClassPath(contextClassLoader));
                    CtClass ctClass = CLASS_POOL.get(currentClass);
                    CtMethod ctMethod = ctClass.getDeclaredMethod("execute");
                    ctMethod.insertBefore("com.xielei.workflowagent.util.FCCustOrderChangeTracer.trace($0.getClass().getName(), $1.getOrderItem(), true);");
                    ctMethod.insertAfter("com.xielei.workflowagent.util.FCCustOrderChangeTracer.trace($0.getClass().getName(), $1.getOrderItem(), false);", true);
                    return ctClass.toBytecode();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return classfileBuffer;
    }

}
