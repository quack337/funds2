package fund;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fund.dto.Code;
import fund.dto.Corporate;
import fund.dto.DonationPurpose;
import fund.dto.pagination.Pagination;
import fund.mapper.CodeMapper;
import fund.mapper.CorporateMapper;
import fund.mapper.DonationPurposeMapper;
import fund.mapper.MenuUserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapper {

    @Autowired MenuUserMapper menuUserMapper;
    @Autowired CorporateMapper corporateMapper;
    @Autowired DonationPurposeMapper donationPurposeMapper;
    @Autowired CodeMapper codeMapper;

    @Test
    public void testMenuUserMapper() {
        assertNotNull(menuUserMapper);

        assertNotNull(menuUserMapper.selectMenuIdByUserId(6));
        assert(menuUserMapper.selectMenuIdByUserId(6).size() > 10);

        assert(menuUserMapper.selectMenuUserByUserId(6).size() > 10);
        menuUserMapper.delete(1, 6);
        menuUserMapper.insert(1, 6);
    }

    @Test
    public void testCorporateMapper() {
        assertNotNull(corporateMapper);

        List<Corporate> list0 = corporateMapper.selectAll();
        assert(list0.size() >= 3);

        Corporate c0 = new Corporate();
        c0.setName("가나");
        c0.setShortName("가");
        c0.setCorporateNo("123");
        c0.setPostCode("345");
        c0.setRoadAddress("나다라");
        c0.setDetailAddress("마바사아");
        c0.setRepresentative("홍");
        corporateMapper.insert(c0);

        List<Corporate> list1 = corporateMapper.selectAll();
        assert(list0.size() + 1 == list1.size());

        Corporate c2 = corporateMapper.selectById(c0.getId());
        assertEquals(c0, c2);

        Corporate c1 = new Corporate();
        c1.setName("가나흥");
        c1.setShortName("가흥");
        c1.setCorporateNo("12344");
        c1.setPostCode("34544");
        c1.setRoadAddress("나다라흥");
        c1.setDetailAddress("마바사아흥");
        c1.setRepresentative("홍흥");
        c1.setId(c0.getId());
        corporateMapper.update(c1);

        c2 = corporateMapper.selectById(c0.getId());
        assertEquals(c1, c2);

        corporateMapper.delete(c0.getId());
        list1 = corporateMapper.selectAll();
        assert(list0.size() == list1.size());
    }

    @Test
    public void testDonationPurposeMapper() {
        assertNotNull(donationPurposeMapper);

        List<DonationPurpose> list0 = donationPurposeMapper.selectAll();
        assert(list0.size() >= 3);

        DonationPurpose o0 = new DonationPurpose();
        o0.setCorporateId(1);
        o0.setOrganizationId(31);
        o0.setCode("BBA");
        o0.setName("가나");
        o0.setGubun("다라");
        o0.setClosed(false);
        o0.setEtc("마바");
        donationPurposeMapper.insert(o0);

        List<DonationPurpose> list1 = donationPurposeMapper.selectAll();
        assert(list0.size() + 1 == list1.size());

        DonationPurpose o2 = donationPurposeMapper.selectById(o0.getId());
        assertEquals(o0, o2);

        DonationPurpose o1 = new DonationPurpose();
        o1.setCorporateId(2);
        o1.setOrganizationId(32);
        o1.setCode("BBB");
        o1.setName("가나다");
        o1.setGubun("다라마");
        o1.setClosed(true);
        o1.setEtc("마바사아");
        o1.setId(o0.getId());
        donationPurposeMapper.update(o1);

        o2 = donationPurposeMapper.selectById(o0.getId());
        assertEquals(o1, o2);

        donationPurposeMapper.delete(o0.getId());
        list1 = donationPurposeMapper.selectAll();
        assert(list0.size() == list1.size());

        ////
        Pagination p = new Pagination();
        p.setPageSize(10);
        p.setCurrentPage(1);
        p.setOrder(0);
        list1 = donationPurposeMapper.selectPage(p);
        assert(list1.size() == 10);
        assert(donationPurposeMapper.selectCount(p) > 0);
    }


    @Test
    public void testCodeMapper() {
        assertNotNull(codeMapper);

        List<Code> list0 = codeMapper.selectEnabledByCodeGroupId(5);
        assert(list0.size() >= 3);

        Code o0 = new Code();
        o0.setCodeGroupId(5);
        o0.setCodeName("가나");
        o0.setState(true);
        o0.setEtc1("가나");
        o0.setEtc2("나다");
        o0.setEtc3("라마");
        codeMapper.insert(o0);

        List<Code> list1 = codeMapper.selectEnabledByCodeGroupId(5);
        assert(list0.size() + 1 == list1.size());

        Code o2 = codeMapper.selectById(o0.getId());
        assertEquals(o0, o2);

        Code o1 = new Code();
        o1.setId(o0.getId());
        o1.setCodeGroupId(5);
        o1.setCodeName("가나ㅁ");
        o1.setState(false);
        o1.setEtc1("가나ㅁ");
        o1.setEtc2("나다ㅁ");
        o1.setEtc3("라마ㅁ");
        codeMapper.update(o1);

        o2 = codeMapper.selectById(o0.getId());
        assertEquals(o1, o2);

        codeMapper.delete(o0.getId());
        list1 = codeMapper.selectEnabledByCodeGroupId(5);
        assert(list0.size() == list1.size());
    }
}
