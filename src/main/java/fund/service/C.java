package fund.service;

public class C {
    public static final int 코드그룹ID_가입구분 = 1;
    public static final int 코드그룹ID_회원구분 = 2;
    public static final int 코드그룹ID_정기납입방법 = 3;
    public static final int 코드그룹ID_비정기납입방법 = 4;
    public static final int 코드그룹ID_소속교회 = 5;
    public static final int 코드그룹ID_은행 = 6;
    public static final int 코드그룹ID_기관종류 = 7;

    public static final int 코드ID_CMS = 10;
    public static final int 코드ID_자동이체 = 11;
    public static final int 코드ID_급여공제 = 12;
    public static final int 코드ID_현물 = 14;

    public static final int 메뉴_기초정보관리 = 1;
    public static final int 메뉴_회원관리_회원관리 = 10;
    public static final int 메뉴_회원관리_우편발송 = 11;
    public static final int 메뉴_회원관리_예우업로드 = 32;
    public static final int 메뉴_금융연동_EB13생성 = 12;
    public static final int 메뉴_금융연동_EB14등록 = 13;
    public static final int 메뉴_금융연동_EB1314결과조회 = 14;
    public static final int 메뉴_금융연동_EB21생성 = 15;
    public static final int 메뉴_금융연동_EB22등록 = 16;
    public static final int 메뉴_금융연동_EB2122결과조회 = 17;
    public static final int 메뉴_금융연동_자동이체결과등록 = 18;
    public static final int 메뉴_금융연동_급여공제결과등록 = 19;
    public static final int 메뉴_납입조회_납입내역조회 = 20;
    public static final int 메뉴_납입조회_회원별납입합계 = 21;
    public static final int 메뉴_납입조회_기부목적별납입합계 = 22;
    public static final int 메뉴_납입조회_회원구분별납입합계 = 23;
    public static final int 메뉴_납입조회_소속교회별납입합계 = 24;
    public static final int 메뉴_영수증_기부금영수증발급대장 = 25;
    public static final int 메뉴_영수증_영수증개별생성 = 26;
    public static final int 메뉴_영수증_영수증일괄생성 = 27;
    public static final int 메뉴_영수증_기부금영수증발급합계 = 33;
    public static final int 메뉴_영수증_국세청보고자료 = 28;
    public static final int 메뉴_증서_장학증서 = 29;
    public static final int 메뉴_증서_기부증서 = 30;
    public static final int 메뉴_기타_일정관리 = 31;

    /* 시스템 관리 메뉴는 "관리자"에게만 허용됨.
     * 그래서 DB에 등록할 필요 없고, 등록하면 메뉴 권한 설정에도 나타나기 때문에 안 됨.
     * 그런데 소스코드 메뉴 권한 검사 부분에는 구현되어 있기 때문에, 아래의 상수는 필요함.
     */
    public static final int 메뉴_시스템관리= 900;
}
